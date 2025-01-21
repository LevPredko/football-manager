INSERT INTO team (name, balance, commission)
SELECT * FROM (VALUES
                   ('Team A', 1000000, 5),
                   ('Team B', 2000000, 3)
              ) AS t(name, balance, commission)
WHERE NOT EXISTS (SELECT 1 FROM team WHERE name = t.name);

INSERT INTO player (name, age, experience_months, transfer_value, team_id)
SELECT * FROM (VALUES
                   ('Player 1', 25, 36, 1000.0, 1),
                   ('Player 2', 22, 24, 1500.0, 2)
              ) AS p(name, age, experience_months, transfer_value, team_id)
WHERE NOT EXISTS (SELECT 1 FROM player WHERE name = p.name);
