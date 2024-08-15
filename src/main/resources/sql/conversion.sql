WITH param AS
(
  select :1 as par
  from dual
)
, tabl As
(
select
  XDATA
, TO_NUMBER(YDATA) as YDATA
, DATASET_NAME
, FILTER_NAME
, FILTER_DATA
from DASHBOARD_DATA
where
     ELEMENT_ID = 'conversion'
 and COALESCE(IS_ACTIVE, '1') = '1'
 and FILTER_NAME = 'DEPARTMENT'
 and FILTER_DATA =  COALESCE((select par from param), FILTER_DATA)
)
SELECT distinct
  t.XDATA as label
, COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and tt.DATASET_NAME = 'QUANTITY_PERSON'), 0) as "Обратилось"
, COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and tt.DATASET_NAME = 'QUANTITY_PERSON_FIRST_CALL'), 0) as "Обратилось впервые"
, COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and tt.DATASET_NAME = 'QUANTITY_PERSON_SKIP'
    and tt.FILTER_DATA = ( CASE WHEN COALESCE((select par from param), '-1') ='-1' THEN '-1' ELSE tt.FILTER_DATA END )
	) , 0)  "% недохода"
FROM tabl t
ORDER BY TO_DATE('01'||t.XDATA, 'dd.mm.yyyy')