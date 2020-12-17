alter table t_measure add column in_canonical_units integer not null;
update t_measure set in_canonical_units = 0;
alter table t_measure add column measure_type integer not null;
update t_measure set measure_type = 1;