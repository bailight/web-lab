CREATE TABLE IF NOT EXISTS PointsWEB(

    id SERIAL PRIMARY KEY,
    x double precision NOT NULL,
    y double precision NOT NULL,
    r double precision NOT NULL,
    checkResult BOOLEAN NOT NULL DEFAULT false,
    clickTime TEXT NOT NULL,
    executionTime TEXT NOT NULL
                                    );