from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy.orm import declarative_base

Base = declarative_base()

class Event(Base): # TODO
    tablename = "events"
    id       = Column(Integer, primary_key=True, autoincrement=True)
    name     = Column(String, nullable=False)          # ТЕАТАР ЗА ДЕЦА И МЛАДИНЦИ
    date_from = Column(String, nullable=True)          # 01-02-2026
    date_to   = Column(String, nullable=True)          # 28-02-2026
    location  = Column(String, nullable=True)          # ако го нема на таа страна -> None
    price     = Column(String, nullable=True)          # ако го нема -> None
    image_url = Column(String, nullable=True)          # URL на постерот
    link      = Column(String, nullable=True)          # https://mktickets.mk/en/event-en/tdm/
    source    = Column(String, nullable=True)          # од која страна е скрејпано (mktickets.mk, ...)