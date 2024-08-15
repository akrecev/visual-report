SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'минут' AS YAxis
, v.COUNT_QUEUE AS "Мать и дитя"
FROM
(
   select t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_WAIT_QUEUE(7)) t
)v