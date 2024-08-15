select
  NAME_DEPARTMENT as "<b>Подразделение</b>"
, PLAN as "<b>План</b>"
, FACT as "<b>Факт</b>"
, VARIATION as "<b>Отклонение</b>"
, PERSENT as "<b>% выполнения</b>"
from table(PKG_DASHBOARD.GET_ExecutionPlanAsDepartment('0'))
