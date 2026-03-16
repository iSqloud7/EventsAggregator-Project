import requests
from bs4 import BeautifulSoup
import re

BASE_URL = "https://mktickets.mk"


def clean_text(text):
    if not text:
        return None

    text = text.replace("\xa0", " ")
    text = text.replace("\n", " ")
    text = text.replace("\r", " ")

    return re.sub(r"\s+", " ", text).strip()


def scrape_event_detail(url):

    response = requests.get(url)

    if response.status_code != 200:
        print(f"Failed to fetch {url}")
        return None

    soup = BeautifulSoup(response.text, "html.parser")

    title_tag = soup.select_one("div.event-top-main > h1")
    image_tag = soup.select_one("div.event-main-image > img.wp-post-image")
    location_tag = soup.select_one("div.event-info > :nth-child(3) > small")
    price_tag = soup.select_one("div.festival-events > ul.tickets > li > p > :nth-child(2)")
    time_start_tag = soup.select_one("div.event-info > :nth-child(5) > :nth-child(2)")
    date_start_tag = soup.select_one("div.event-info > :nth-child(2) > :nth-child(2)")
    city_tag = soup.select_one("div.event-info > :nth-child(4) > :nth-child(2)")
    description_tag = soup.select_one("div.event-content")

    image_url = image_tag["src"] if image_tag and image_tag.get("src") else None

    event = {
        "title": clean_text(title_tag.get_text()) if title_tag else None,
        "image_url": image_url,
        "location": clean_text(location_tag.get_text()) if location_tag else None,
        "price": clean_text(price_tag.get_text()).replace("Price:", "").strip() if price_tag else None,
        "time_start": clean_text(time_start_tag.get_text()) if time_start_tag else None,
        "date_start": clean_text(date_start_tag.get_text()) if date_start_tag else None,
        "city": clean_text(city_tag.get_text()) if city_tag else None,
        "description": clean_text(description_tag.get_text()) if description_tag else None,
    }

    return event


def run_scraper():

    response = requests.get(BASE_URL)

    if response.status_code != 200:
        print("Failed to fetch homepage")
        return []

    soup = BeautifulSoup(response.text, "html.parser")

    detail_links = [
        a["href"]
        for a in soup.select("a.poster-link")
        if a.get("href")
    ]

    unique_links = list(set(detail_links))

    print(f"Found {len(unique_links)} event links")

    events = []

    for link in unique_links:

        event = scrape_event_detail(link)

        if event:
            events.append(event)

    return events