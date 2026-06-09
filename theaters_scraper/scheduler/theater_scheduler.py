import schedule
import time
import sys
import os

BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..'))
sys.path.insert(0, BASE_DIR)
sys.path.insert(0, os.path.join(BASE_DIR, 'theaters_scraper'))
sys.path.insert(0, os.path.join(BASE_DIR, 'scraper_postgres_connector'))

from scrapers.scraper_theater import run_teatar_scraper
from scraper_postgres_connector.services.theater_show_service import load_theater_shows_from_json
from db.database import engine
from db.models import Base

Base.metadata.create_all(bind=engine)


def theater_job():
    print("\n==============================================")
    print("Starting theater scraper...")
    print("==============================================")
    try:
        shows = run_teatar_scraper()

        if shows:
            load_theater_shows_from_json(shows)

    except Exception as e:
        print(f"Error in theater scheduler: {e}")
    print("==============================================\n")

schedule.every(24).hours.do(theater_job)

print("Theater Scheduler is successfully running...")
theater_job()

while True:
    schedule.run_pending()
    time.sleep(60)