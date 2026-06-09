from db.database import Session
from db.models import TheaterShow

def load_theater_shows_from_json(shows: list):
    session = Session()

    try:
        for s in shows:
            exists = session.query(TheaterShow).filter_by(
                title=s["title"],
                date_start=s.get("date_start")
            ).first()

            if exists:
                exists.image = s.get("image_url")
                exists.description = s.get("description")
                exists.location = s.get("location")
                exists.city = s.get("city")
                exists.price = s.get("price", "300 ден.")
                exists.time_start = s.get("time_start", "20:00")
            else:
                show = TheaterShow(
                    title=s["title"],
                    image=s.get("image_url"),
                    location=s.get("location"),
                    city=s.get("city"),
                    price=s.get("price", "300 ден."),
                    time_start=s.get("time_start", "20:00"),
                    date_start=s.get("date_start"),
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