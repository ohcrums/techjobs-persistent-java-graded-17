--Part 1
-- `job` table columns and data types:
-- id - type = INT
-- employer - type = varchar(255)
-- name - type = varchar(255)
-- skills - type = varchar(255)

--Part 2
SELECT name FROM employer WHERE location = "St. Louis City";

--Part 3
DROP TABLE job;

--Part 4
SELECT * FROM skill
LEFT JOIN job_skills
ON job_skills.skills_id = skill.id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;