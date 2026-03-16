from flask import Flask, jsonify
from flask_cors import CORS

from scraper_postgres_connector.db.database import Session
from scraper_postgres_connector.db.models import Event

app = Flask(__name__)
CORS(app)


@app.route("/events")
def get_events():
    session = Session()
    try:
        events = session.query(Event).all()
        return jsonify([e.to_dict() for e in events])
    finally:
        session.close()


if __name__ == "__main__":
    app.run(debug=True)