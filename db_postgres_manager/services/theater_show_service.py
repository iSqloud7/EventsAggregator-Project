from db.database import Session
from db.models import TheaterShow

def load_theater_shows_from_json(shows: list):
    session = Session()

    try:
        for s in shows:
            existing_show = session.query(TheaterShow).filter_by(title=s["title"]).first()

            if existing_show:
                existing_show.image = s.get("image_url")
                existing_show.description = s.get("description")
                existing_show.location = s.get("location")
                existing_show.city = s.get("city")
                existing_show.price = s.get("price", "600 ден.")
                existing_show.time_start = s.get("time_start", "20:00")
            else:
                show = TheaterShow(
                    title=s["title"],
                    image=s.get("image_url"),
                    location=s.get("location"),
                    city=s.get("city"),
                    price=s.get("price", "600 ден."),
                    time_start=s.get("time_start", "20:00"),
                    date_start=None,
                    description=s.get("description")
                )
                session.add(show)

        session.commit()
        print(f"Theater shows synced. {len(shows)} shows processed.")
    except Exception as e:
        session.rollback()
        print(f"Database error during sync: {e}")
    finally:
        session.close()