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

from theaters_scraper.scrapers.scraper_theater import run_theatar_scraper
from services.theater_show_service import load_theater_shows_from_json


def theater_job():
    print("Starting scraping job...")

    shows = run_theatar_scraper()

    if shows:
        load_theater_shows_from_json(shows)
        print(f"Inserted {len(shows)} shows into database!")
    else:
        print("No shows scraped!")

theater_job()

# schedule.every(24).hours.do(theater_job)
# print("Theater Scheduler is successfully running...")

# while True:
#     schedule.run_pending()
#     time.sleep(60)