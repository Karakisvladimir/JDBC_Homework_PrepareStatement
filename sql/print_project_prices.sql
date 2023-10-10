-- Запит для виведення найдородшого проекту
SELECT c.name, SUM(w.salary * DATEDIFF('MONTH', p.start_date, p.finish_date)) AS price
FROM project p
JOIN client c ON p.client_id = c.id
JOIN project_worker pw ON p.id = pw.project_id
JOIN worker w ON pw.worker_id = w.id
GROUP BY c.name
HAVING
   SUM(w.salary * DATEDIFF('MONTH', p.start_date, p.finish_date)) = (
      SELECT MAX(max_price) FROM (
         SELECT c.name, SUM(w.salary * DATEDIFF('MONTH', p.start_date, p.finish_date)) AS max_price
         FROM project p
         JOIN client c ON p.client_id = c.id
         JOIN project_worker pw ON p.id = pw.project_id
         JOIN worker w ON pw.worker_id = w.id
         GROUP BY c.name
      ) AS max_price
   )

UNION ALL

-- Запит для виведення найдешевшого проекту
SELECT c.name, SUM(w.salary * DATEDIFF('MONTH', p.start_date, p.finish_date)) AS price
FROM project p
JOIN client c ON p.client_id = c.id
JOIN project_worker pw ON p.id = pw.project_id
JOIN worker w ON pw.worker_id = w.id
GROUP BY c.name
HAVING SUM(w.salary * DATEDIFF('MONTH', p.start_date, p.finish_date)) = (
   SELECT MIN(min_price) FROM (
      SELECT c.name, SUM(w.salary * DATEDIFF('MONTH', p.start_date, p.finish_date)) AS min_price
      FROM project p
      JOIN client c ON p.client_id = c.id
      JOIN project_worker pw ON p.id = pw.project_id
      JOIN worker w ON pw.worker_id = w.id
      GROUP BY c.name
   ) AS min_price
)
ORDER BY price DESC;