import re
import json
from datetime import datetime
from playwright.sync_api import sync_playwright

BASE_URL = "https://www.cineplexx.mk/film?category=now&location=all&date={date}"
LOCATION = "Cineplexx Skopje"
CITY = "Скопје"


def clean_text(text):
    if not text:
        return None
    text = text.replace("\xa0", " ").replace("\n", " ").replace("\r", " ")
    return re.sub(r"\s+", " ", text).strip()


def format_date(raw: str) -> str:
    """YYYY-MM-DD → DD-MM-YYYY"""
    try:
        return datetime.strptime(raw, "%Y-%m-%d").strftime("%d-%m-%Y")
    except Exception:
        return raw


def parse_from_api(api_data: list) -> list[dict]:
    films = []

    for resp in api_data:
        data = resp.get("data", resp)

        items = []
        if isinstance(data, list):
            items = data
        elif isinstance(data, dict):
            for key in ("films", "movies", "data", "results", "items"):
                if key in data and isinstance(data[key], list):
                    items = data[key]
                    break

        for item in items:
            title = item.get("title") or item.get("name") or item.get("movieTitle")
            if not title:
                continue

            image = (
                item.get("posterUrl")
                or item.get("poster_url")
                or item.get("poster")
                or item.get("image")
                or item.get("imageUrl")
                or item.get("image_url")
            )

            genre = item.get("genre") or item.get("genres") or item.get("category") or ""
            if isinstance(genre, list):
                genre = ", ".join(genre)

            duration = item.get("duration") or item.get("runtime") or ""
            if duration:
                duration = f"{duration} min"

            description = item.get("synopsis") or item.get("description") or item.get("plot")

            showtimes = item.get("showtimes") or item.get("showTimes") or item.get("screenings") or []
            date_start = None
            time_start = None

            if isinstance(showtimes, list) and showtimes:
                first = showtimes[0]
                raw_dt = (
                    first.get("time")
                    or first.get("datetime")
                    or first.get("startTime")
                    or ""
                )

                if "T" in raw_dt:
                    parts = raw_dt.split("T")
                    date_start = format_date(parts[0])
                    time_start = parts[1][:5] if len(parts) > 1 else None
                elif " " in raw_dt:
                    parts = raw_dt.split(" ")
                    date_start = format_date(parts[0])
                    time_start = parts[1][:5] if len(parts) > 1 else None

            price = item.get("price") or item.get("ticketPrice")

            films.append({
                "title": clean_text(title),
                "image_url": image,
                "genre": clean_text(genre) if genre else None,
                "duration": clean_text(duration) if duration else None,
                "time_start": time_start,
                "date_start": date_start,
                "location": LOCATION,
                "city": CITY,
                "price": str(price) if price else None,
                "description": clean_text(description),
            })

    return films


def parse_from_dom(page) -> list[dict]:
    films = []

    selectors = [
        "[class*='FilmCard']", "[class*='film-card']",
        "[class*='MovieCard']", "[class*='movie-card']",
        "[class*='film-item']", "[class*='movie-item']",
        "article[class*='film']", "li[class*='film']",
    ]

    cards = []

    for sel in selectors:
        cards = page.query_selector_all(sel)
        if cards:
            print(f"  DOM: {len(cards)} cards via '{sel}'")
            break

    for card in cards:

        def get_text(selector):
            el = card.query_selector(selector)
            return clean_text(el.inner_text()) if el else None

        def get_attr(selector, attr):
            el = card.query_selector(selector)
            return el.get_attribute(attr) if el else None

        title = get_text("h1, h2, h3, h4, [class*='title'], [class*='Title']")
        image = get_attr("img", "src") or get_attr("img", "data-src")
        genre = get_text("[class*='genre'], [class*='Genre']")
        duration = get_text("[class*='duration'], [class*='Duration'], [class*='runtime']")
        time_start = get_text("[class*='time'], [class*='Time'], [class*='showtime']")
        date_start = get_text("[class*='date'], [class*='Date']")
        price = get_text("[class*='price'], [class*='Price']")

        if title:
            films.append({
                "title": title,
                "image_url": image,
                "genre": genre,
                "duration": duration,
                "time_start": time_start,
                "date_start": date_start,
                "location": LOCATION,
                "city": CITY,
                "price": price,
                "description": None,
            })

    return films


def scrape_cineplexx(date: str = None) -> list[dict]:
    if not date:
        date = datetime.today().strftime("%Y-%m-%d")

    url = BASE_URL.format(date=date)
    api_responses = []

    print(f"Scraping Cineplexx: {url}")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            user_agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
        )
        page = context.new_page()

        def on_response(response):
            if response.status == 200 and "json" in response.headers.get("content-type", ""):
                try:
                    data = response.json()
                    data_str = json.dumps(data).lower()

                    if any(k in data_str for k in ["title", "movie", "film", "poster"]):
                        api_responses.append({"url": response.url, "data": data})
                        print(f"  [API] {response.url}")

                except Exception:
                    pass

        page.on("response", on_response)

        try:
            page.goto(url, wait_until="networkidle", timeout=30000)
        except Exception as e:
            print(f"  Warning: {e}")

        films = parse_from_api(api_responses) if api_responses else []

        if not films:
            print("  DOM fallback...")
            try:
                page.wait_for_selector("[class*='film'], [class*='movie']", timeout=8000)
            except Exception:
                pass

            films = parse_from_dom(page)

        browser.close()

    # dedupe
    seen = set()
    unique = []

    for f in films:
        if f["title"] and f["title"] not in seen:
            seen.add(f["title"])
            unique.append(f)

    print(f"Total unique films: {len(unique)}")
    return unique


def run_scraper():
    return scrape_cineplexx()


if __name__ == "__main__":
    films = run_scraper()
    print(json.dumps(films[:3], ensure_ascii=False, indent=2))