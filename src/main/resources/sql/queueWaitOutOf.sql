SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'мминут' AS YAxis
, v.COUNT_QUEUE AS "Вне очереди"
FROM
(
   select t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_WAIT_QUEUE(10)) t
)v