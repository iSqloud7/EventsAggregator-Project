import schedule
import time
import sys
from pathlib import Path

# Add project root to path so all modules are accessible.
ROOT_DIR = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT_DIR))

# Add scraper_postgres_connector to path for relative imports.
CONNECTOR_DIR = ROOT_DIR / "scraper_postgres_connector"
sys.path.insert(0, str(CONNECTOR_DIR))

from mktickets_scraper.scrapers.scraper_prod import run_scraper
from services.event_service import load_events_from_json


def job():
    print("Starting scraping job...")

    events = run_scraper()

    if events:
        load_events_from_json(events)
        print(f"Inserted {len(events)} events into database!")
    else:
        print("No events scraped!")


schedule.every(24).hours.do(job)

print("Scheduler started - running every 24 hours!")

job()

while True:
    schedule.run_pending()
    time.sleep(60)
