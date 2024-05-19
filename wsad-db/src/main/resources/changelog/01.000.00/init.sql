--liquibase formatted sql
--changeset zyablitseva-av:web-session
CREATE TABLE SESSION (
    ID                          BIGSERIAL       PRIMARY KEY,
    SESSION_ID                  VARCHAR(128)    NOT NULL unique,
    START_OF_SESSION            TIMESTAMP       NOT NULL,
    END_OF_SESSION              TIMESTAMP,
    CLICKS_NUMBER               BIGSERIAL       NOT NULL,
    IMAGE_REQUESTS              BIGSERIAL       NOT NULL,
    PDF_REQUESTS                BIGSERIAL       NOT NULL,
    ERRORS_REQUESTS             BIGSERIAL       NOT NULL,
    SPECIAL_REQUESTS            BIGSERIAL       NOT NULL,
    ROBOT_FILE_REQUESTS         BIGSERIAL       NOT NULL,
    TOTAL_DEVIATION_OF_DEATH    BIGSERIAL       NOT NULL,
    TOTAL_DIRECT_REQUEST        BIGSERIAL       NOT NULL,
    WEIGHT                      BIGSERIAL       NOT NULL,
    LAST_REQUEST_LINK           BIGSERIAL       NOT NULL,
    LAST_MODIFICATION           TIMESTAMP       NOT NULL,
    IS_CHANGED                  BOOLEAN         DEFAULT FALSE,
    SESSION_TYPE                VARCHAR(128)
);

COMMENT ON TABLE SESSION IS 'Параметры сессии';
COMMENT ON COLUMN SESSION.ID  IS 'ID клиента';
COMMENT ON COLUMN SESSION.SESSION_ID  IS 'Строковый идентификатор сессии';
COMMENT ON COLUMN SESSION.START_OF_SESSION  IS 'Время начала сесии';
COMMENT ON COLUMN SESSION.END_OF_SESSION  IS 'Время окончания сессии';
COMMENT ON COLUMN SESSION.CLICKS_NUMBER  IS 'Количество запросов';
COMMENT ON COLUMN SESSION.IMAGE_REQUESTS  IS 'Количество запросов изображений';
COMMENT ON COLUMN SESSION.PDF_REQUESTS  IS 'Количество запросов PDF';
COMMENT ON COLUMN SESSION.ERRORS_REQUESTS  IS 'Количество запросов с ошибкой';
COMMENT ON COLUMN SESSION.SPECIAL_REQUESTS  IS 'Количество специальных запросов';
COMMENT ON COLUMN SESSION.ROBOT_FILE_REQUESTS  IS 'Количество запросов robot.txt';
COMMENT ON COLUMN SESSION.TOTAL_DEVIATION_OF_DEATH  IS 'Общая глубина отклонения';
COMMENT ON COLUMN SESSION.TOTAL_DIRECT_REQUEST  IS 'Количество прямых запросов';
COMMENT ON COLUMN SESSION.WEIGHT  IS 'Количество запрошенных байтов';
COMMENT ON COLUMN SESSION.LAST_REQUEST_LINK  IS 'URL последнего запроса';
COMMENT ON COLUMN SESSION.IS_CHANGED  IS 'Признак изменения сессии после завершения';
COMMENT ON COLUMN SESSION.SESSION_TYPE  IS 'Тип сессии';
--rollback DROP TABLE SESSION;

--changeset zyablitseva-av:scheduler
create table SHEDLOCK (
    NAME       varchar(64)  not null constraint SHEDLOCK_PK primary key,
    LOCK_UNTIL timestamp    not null,
    LOCKED_AT  timestamp    not null,
    LOCKED_BY  varchar(255) not null
);

comment on table SHEDLOCK is 'Блокировка запуска задач шедулера';
comment on column SHEDLOCK.NAME is 'Наименование блокировки';
comment on column SHEDLOCK.LOCK_UNTIL is 'Максимальное время действия блокировки';
comment on column SHEDLOCK.LOCKED_AT is 'Время взятия блокировки';
comment on column SHEDLOCK.LOCKED_BY is 'Имя хоста, взявшего блокировку';
--rollback drop table SHEDLOCK;