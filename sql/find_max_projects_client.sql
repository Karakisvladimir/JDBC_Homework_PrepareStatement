-- Запити для пошуку клієнта з найбільшою кількістю проектів
SELECT c.name, count(p.client_id) AS projects_client FROM client c
JOIN project p ON p.client_id = c.id
GROUP BY client_id
HAVING projects_client  IN (
	SELECT count(client_id)
	FROM project
	GROUP BY client_id
	ORDER BY count(projects_client ) DESC
	LIMIT 1
);

SELECT c.name, COUNT(p.client_id) AS projects_client FROM client c
JOIN project p ON p.client_id = c.id
GROUP BY client_id
HAVING projects_client = (
	SELECT MAX(proj) FROM (
		SELECT COUNT(client_id) AS proj
		FROM project
		GROUP BY client_id
   )
);
