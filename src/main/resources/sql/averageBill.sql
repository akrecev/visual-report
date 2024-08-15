WITH tabl AS
(
select
  XDATA
, TO_NUMBER(YDATA) as YDATA
, DATASET_NAME
, FILTER_NAME
, FILTER_DATA
, count(distinct FILTER_DATA) over (partition by XDATA) as count_dep
from DASHBOARD_DATA
where
     ELEMENT_ID = 'averageBill'
 and COALESCE(IS_ACTIVE, '1') = '1'
 and FILTER_NAME = 'DEPARTMENT'
 and FILTER_DATA =  COALESCE(:1, FILTER_DATA)
)
SELECT distinct
  t.XDATA as label
, TO_CHAR(case
    when COALESCE(MAX(count_dep), 0) != 0
       then ROUND(COALESCE(SUM(YDATA)/MAX(count_dep), 0), 0)
       else 0
   end) as "Факт"
FROM tabl t
GROUP BY t.XDATA
ORDER BY TO_DATE('01'||t.XDATA, 'dd.mm.yyyy')