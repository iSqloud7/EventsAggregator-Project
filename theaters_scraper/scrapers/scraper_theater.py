import requests
from bs4 import BeautifulSoup
import re

BASE_URL = "https://teatarkomedija.mk"


def clean_text(text):
    if not text:
        return None
    text = text.replace("\xa0", " ").replace("\n", " ").replace("\r", " ")
    return re.sub(r"\s+", " ", text).strip()


def get_theater_address_info(soup):
    footer_widget = soup.select_one("div.textwidget")
    if not footer_widget:
        return {"location": "Театар Комедија", "city": "Скопје"}

    raw_text = footer_widget.get_text(separator=" ", strip=True)
    clean_text_part = raw_text.replace("НУ Театар Комедија © 2026", "").strip()
    full_address = clean_text_part.split('Администрација')[0].strip().rstrip(', ')

    parts = full_address.split(',', 1)
    location = parts[0].strip()
    city = parts[1].strip() if len(parts) > 1 else "Скопје"
    return {"location": location, "city": city}


def get_theater_description(url):
    try:
        response = requests.get(url)
        if response.status_code != 200:
            return None

        soup = BeautifulSoup(response.text, "html.parser")
        description_tag = soup.select_one("div.qodef-grid-item")

        return clean_text(description_tag.get_text()) if description_tag else None
    except Exception as e:
        print(f"Error scraping {url}: {e}!")
        return None


def get_show_details(url):
    try:
        response = requests.get(url)
        if response.status_code != 200: return None

        soup = BeautifulSoup(response.text, "html.parser")

        description_tag = soup.select_one("div.qodef-grid-item")
        description = clean_text(description_tag.get_text()) if description_tag else ""

        return {"description": description}
    except Exception as e:
        print(f"Error: {e}")
        return None


def run_theater_scraper():
    response = requests.get(BASE_URL)
    if response.status_code != 200: return []

    soup = BeautifulSoup(response.text, "html.parser")
    address_info = get_theater_address_info(soup)

    all_cards = soup.select("article.qodef-e")

    shows = []
    for card in all_cards:
        title_tag = card.select_one("h5.qodef-e-title")
        link_tag = card.select_one("a.qodef-e-title-link")
        img_tag = card.select_one("img.wp-post-image")

        if not title_tag or not link_tag: continue

        link = link_tag['href']

        details = get_show_details(link)

        show_entry = {
            "title": clean_text(title_tag.get_text()),
            "image_url": img_tag['src'] if img_tag else None,
            "location": address_info["location"],
            "city": address_info["city"],
            "price": "600 ден.",
            "time_start": "20:00",
            "date_start": None,
            "description": details["description"] if details else ""
        }
        shows.append(show_entry)

    return shows
