SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'минут' AS YAxis
, v.COUNT_QUEUE AS "Test"
FROM
(
   select  t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_WAIT_QUEUE_BLOOD(5)) t
)v