import schedule
import time
import sys
from pathlib import Path

ROOT_DIR = Path(__file__).resolve().parents[2]
sys.path.append(str(ROOT_DIR))

from mktickets_scraper.scrapers.scraper_prod import run_scraper
from scraper_postgres_connector.services.event_service import load_events_from_json


def job():
    print("Starting scraping job...")

    events = run_scraper()

    if events:
        load_events_from_json(events)
        print(f"Inserted {len(events)} events into database")
    else:
        print("No events scraped")


schedule.every(24).hours.do(job)

print("Scheduler started - running every 24 hours")

job()

while True:
    schedule.run_pending()
    time.sleep(60)