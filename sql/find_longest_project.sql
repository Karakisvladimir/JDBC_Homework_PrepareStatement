SELECT c.name,
DATEDIFF('MONTH', p.START_DATE, p.FINISH_DATE) AS month_count
FROM client c
JOIN project p ON p.client_id = c.id
WHERE DATEDIFF('MONTH', p.START_DATE, p.FINISH_DATE) = (
   SELECT MAX(project_duration)
   FROM (SELECT DATEDIFF('MONTH', START_DATE, FINISH_DATE)
	AS project_duration FROM project) AS project_duration
);
