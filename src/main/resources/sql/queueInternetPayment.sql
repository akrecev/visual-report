SELECT
  v.HOUR_QUEUE AS label
, 'час' AS XAxis
, 'человек' AS YAxis
, v.COUNT_QUEUE AS "Опл.через Internet"
FROM
(
   select t.HOUR_QUEUE, t.COUNT_QUEUE
   from table(PKG_DASHBOARD.GET_QUEUE(6)) t
)v