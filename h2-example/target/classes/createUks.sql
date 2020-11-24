DROP TABLE UKS;
CREATE TABLE UKS
(
  SNR                 DECIMAL  (009, 00)  NOT NULL
, NAME                CHAR     (035    )  NOT NULL
, GEBNAME             CHAR     (035    )  NOT NULL
   );

CREATE UNIQUE INDEX UKSKEY1 ON UKS
(
   SNR                 ASC
);
