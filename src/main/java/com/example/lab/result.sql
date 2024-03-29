select count(*)
from (select count(c.*) comment_count, p.*
      from posts p
               left join comments c on p.id = c.post_id
      group by p.id
      having count(c.*) > -1) t;


select count((select count(c1_0.id)
              from posts p1_0
                       left join comments c1_0 on p1_0.id = c1_0.post_id
              where count(c1_0.id) >?
              group by p1_0.id));

select count((select count(c1_0.id)
              from posts p1_0
                       left join comments c1_0 on p1_0.id = c1_0.post_id
              group by p1_0.id
              having count(c1_0.id) > 1));

SELECT COUNT(*) AS posts_with_multiple_comments
FROM (SELECT p1_0.id
      FROM posts p1_0
               LEFT JOIN comments c1_0 ON p1_0.id = c1_0.post_id
      GROUP BY p1_0.id
      HAVING COUNT(c1_0.id) > 1) AS subquery;