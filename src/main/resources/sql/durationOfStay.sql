WITH tabl AS
(
select
  XDATA
, TO_NUMBER(YDATA) as YDATA
, DATASET_NAME
, FILTER_NAME
, FILTER_DATA
from DASHBOARD_DATA
where
     ELEMENT_ID = 'durationOfStay'
 and COALESCE(IS_ACTIVE, '1') = '1'
 and FILTER_NAME = 'DEPARTMENT'
 and FILTER_DATA =  COALESCE(:1, FILTER_DATA)
 and COALESCE(YDATA, '0') <> '0'
)
SELECT distinct
  t.XDATA as label
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '3'), 0)) as "КО"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '4'), 0)) as "ТО"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '12'), 0)) as "ДС"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '14'), 0)) as "РХМД"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '83'), 0)) as "ЛОР"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '16'), 0)) as "Лучевая диагн."
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '19'), 0)) as "УЗИ"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '18'), 0)) as "Функц. диагностика"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '8'), 0)) as "Физиотерапия"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '17'), 0)) as "Эндоскопия"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '66'), 0)) as "Молекулярная биология"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '299'), 0)) as "Молек. биол."
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '23'), 0)) as "Цитология"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '59'), 0)) as "Микробиология"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '22'), 0)) as "Иммунология"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '21'), 0)) as "Клинико-гематология"
, MAX(COALESCE((SELECT SUM(COALESCE(tt.YDATA, 0)) FROM tabl tt WHERE t.XDATA = tt.XDATA and t.FILTER_DATA = tt.FILTER_DATA and t.FILTER_DATA = '58'), 0)) as "Биохимия"
FROM tabl t
GROUP BY t.XDATA
ORDER BY TO_DATE('01'||t.XDATA, 'dd.mm.yyyy')