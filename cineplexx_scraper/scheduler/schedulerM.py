import schedule
import time
import sys
from pathlib import Path

from scraper_postgres_connector.services.movie_service import load_movies_from_json

ROOT_DIR = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT_DIR))

CONNECTOR_DIR = ROOT_DIR / "scraper_postgres_connector"
sys.path.insert(0, str(CONNECTOR_DIR))

from cineplexx_scraper.scrapers.scraperMovie import run_scraper


def job():
    print("Starting Cineplexx scraping job...")
    films = run_scraper()
    if films:
        load_movies_from_json(films)
        print(f"Done! {len(films)} films processed.")
    else:
        print("No films scraped.")


schedule.every(24).hours.do(job)
print("Cineplexx scheduler started — every 24 hours")

job()

while True:
    schedule.run_pending()
    time.sleep(60)