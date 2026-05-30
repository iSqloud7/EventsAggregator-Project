import psycopg2
import os
from dotenv import load_dotenv

load_dotenv()

DB_CONFIG = {
    "host":     os.getenv("DB_HOST", "localhost"),
    "port":     os.getenv("DB_PORT", 5432),
    "dbname":   os.getenv("DB_NAME", "events-aggregator-db"),
    "user":     os.getenv("DB_USER", "postgres"),
    "password": os.getenv("DB_PASSWORD", ""),
}


def get_connection():
    return psycopg2.connect(**DB_CONFIG)


def load_movies_from_json(movies: list[dict]):
    """
    Вметнува или ажурира филмови во MOVIES табелата.
    Дедуплицира по title + date_start.
    """
    if not movies:
        print("No movies to insert.")
        return

    conn = get_connection()
    cursor = conn.cursor()

    inserted = 0
    skipped = 0

    for movie in movies:
        title      = movie.get("title")
        image      = movie.get("image_url") or movie.get("image")
        genre      = movie.get("genre")
        duration   = movie.get("duration")
        time_start = movie.get("time_start")
        date_start = movie.get("date_start")
        location   = movie.get("location", "Cineplexx Skopje")
        city       = movie.get("city", "Скопје")
        price      = movie.get("price")
        description = movie.get("description")

        if not title:
            skipped += 1
            continue

        # Провери дали веќе постои (по наслов + датум)
        cursor.execute(
            "SELECT id FROM movies WHERE title = %s AND date_start = %s",
            (title, date_start)
        )
        existing = cursor.fetchone()

        if existing:
            # Ажурирај ако веќе постои
            cursor.execute("""
                UPDATE movies SET
                    image = %s, genre = %s, duration = %s,
                    time_start = %s, location = %s, city = %s,
                    price = %s, description = %s
                WHERE title = %s AND date_start = %s
            """, (image, genre, duration, time_start, location, city,
                  price, description, title, date_start))
            skipped += 1
        else:
            cursor.execute("""
                INSERT INTO movies
                    (title, image, genre, duration, time_start, date_start,
                     location, city, price, description)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            """, (title, image, genre, duration, time_start, date_start,
                  location, city, price, description))
            inserted += 1

    conn.commit()
    cursor.close()
    conn.close()

    print(f"Movies: {inserted} inserted, {skipped} skipped/updated.")