SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'человек' AS YAxis
, v.COUNT_QUEUE AS "СКТ, МРТ"
FROM
(
   select t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_QUEUE(3)) t
)v