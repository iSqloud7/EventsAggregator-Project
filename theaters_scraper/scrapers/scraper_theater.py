import requests
from bs4 import BeautifulSoup
import re

BASE_URL = "https://teatarkomedija.mk"
REPERTOAR_URL = "https://teatarkomedija.mk/repertoar/"


def clean_text(text):
    if not text:
        return ""
    text = text.replace("\xa0", " ").replace("\n", " ").replace("\r", " ")
    return re.sub(r"\s+", " ", text).strip()

def scrape_show_detail(url):
    try:
        response = requests.get(url, timeout=10)
        if response.status_code != 200:
            return None

        soup = BeautifulSoup(response.text, "html.parser")
        columns = soup.select('div.elementor-column.elementor-col-50')
        if len(columns) >= 2:
            description_text = columns[1].get_text(separator="\n", strip=True)
            if description_text and len(description_text) > 50:
                return {"description": clean_text(description_text)}

        content_areas = soup.select(".elementor-widget-text-editor, .entry-content")

        longest_text = ""
        for area in content_areas:
            text = area.get_text(separator="\n", strip=True)
            if len(text) > len(longest_text):
                longest_text = text

        if longest_text and len(longest_text) > 50:
            return {"description": clean_text(longest_text)}

        return {"description": "Опис моментално недостапен."}

    except Exception as e:
        print(f"Error scraping detail {url}: {e}")
        return None



def run_teatar_scraper():
    try:
        response = requests.get(REPERTOAR_URL, timeout=10)
        if response.status_code != 200:
            print("Failed to fetch teatarkomedija.mk")
            return []

        soup = BeautifulSoup(response.text, "html.parser")

        seen_urls = set()
        shows = []

        articles = soup.select("article.qodef-e")

        for article in articles:
            link_tag = article.select_one("a.qodef-e-title-link")
            img_tag = article.select_one("img.wp-post-image")
            title_tag = article.select_one("h5.qodef-e-title")

            if not link_tag:
                continue

            url = link_tag.get("href")

            if url in seen_urls:
                continue
            seen_urls.add(url)

            title = clean_text(title_tag.get_text()) if title_tag else None

            if not title:
                continue

            image = img_tag.get("src") if img_tag else None

            details = scrape_show_detail(url)
            description = details.get("description") if details else None

            show = {
                "title": title,
                "image_url": image,
                "location": "Театар Комедија",
                "city": "Скопје",
                "price": "300 ден.",
                "time_start": "20:00",
                "date_start": None,
                "description": description
            }

            shows.append(show)
            print(f"Scraped: {title}")

        print(f"Total theater shows scraped: {len(shows)}")
        return shows

    except Exception as e:
        print(f"Error: {e}")
        return []