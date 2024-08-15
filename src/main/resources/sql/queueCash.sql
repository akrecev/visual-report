SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'человек' AS YAxis
, v.COUNT_QUEUE AS "Касса"
FROM
(
   select  t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_QUEUE(5)) t
)v