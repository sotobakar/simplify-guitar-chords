CREATE TABLE songs (
                       id SERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       singer TEXT NOT NULL,
                       genre TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW()
                   );

CREATE TABLE chords (
                        id SERIAL PRIMARY KEY,
                        chord TEXT NOT NULL,
                        capo INTEGER NOT NULL,
                        open_chords INTEGER NOT NULL,
                        barre_chords INTEGER NOT NULL,
                        chord_changes INTEGER NOT NULL,
                        finger_placement INTEGER NOT NULL,
                        song_id INTEGER NOT NULL REFERENCES songs(id),
                        difficulty INTEGER NOT NULL,
                        created_at TIMESTAMP DEFAULT NOW(),
                        updated_at TIMESTAMP DEFAULT NOW()
                    );
