SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'человек' AS YAxis
, v.COUNT_QUEUE AS "Печать пропуска"
FROM
(
   select t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_QUEUE(8)) t
)v