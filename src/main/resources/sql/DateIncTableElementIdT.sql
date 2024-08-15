select
  ELEMENT_ID as "Таблица"
, LAST_UPDATED as "Дата"
from table(PKG_DASHBOARD.GET_LAST_DATE_BUILD_TBL_T(:1))