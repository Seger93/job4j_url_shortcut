CREATE TABLE "UniqueCode" (
                              id SERIAL PRIMARY KEY,
                              code VARCHAR(255) NOT NULL,
                              url_id INT NOT NULL REFERENCES "Url" ("id")
);