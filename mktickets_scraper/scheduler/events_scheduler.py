import schedule
import time
import sys
from pathlib import Path

# Add project root to path so all modules are accessible.
ROOT_DIR = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT_DIR))

# Add db_postgres_manager to path for relative imports.
CONNECTOR_DIR = ROOT_DIR / "db_postgres_manager"
sys.path.insert(0, str(CONNECTOR_DIR))

from mktickets_scraper.scrapers.scraper_prod import run_event_scraper
from services.event_service import load_events_from_json


def event_job():
    print("Starting scraping job...")

    events = run_event_scraper()

    if events:
        load_events_from_json(events)
        print(f"Inserted {len(events)} events into database!")
    else:
        print("No events scraped!")

event_job()

print("Task completed successfully.")
sys.exit(0)

# schedule.every(24).hours.do(event_job)
# print("Scheduler started - running every 24 hours!")

# while True:
#     schedule.run_pending()
#     time.sleep(10)
