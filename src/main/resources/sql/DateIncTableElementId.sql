select
  ELEMENT_ID as "График"
, LAST_UPDATED as "Дата"
from table(PKG_DASHBOARD.GET_LAST_DATE_BUILD_TBL(:1))