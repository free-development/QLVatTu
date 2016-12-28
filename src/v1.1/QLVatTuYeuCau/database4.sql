/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     02/08/2015 8:03:04 CH                        */
/*==============================================================*/
drop database if exists vattu;
create database if not exists vattu DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_unicode_ci;
CREATE SCHEMA IF NOT EXISTS vattu DEFAULT CHARACTER SET utf8 ;
use vattu;

drop table if exists CHATLUONG;

drop table if exists CHUCDANH;

drop table if exists CONGVAN;

drop table if exists CTNGUOIDUNG;

drop table if exists CTVATTU;

drop table if exists DONVI;

drop table if exists FILE;

drop table if exists MUCDICH;

drop table if exists NGUOIDUNG;

drop table if exists NOISANXUAT;

drop table if exists PHIEUNHAP;

drop table if exists PHIEUXUAT;

drop table if exists TRANGTHAI;

drop table if exists VAITRO;

drop table if exists VATTU;

drop table if exists DONVITINH;

drop table if exists VATTUNHAP;

drop table if exists VATTUXUAT;

drop table if exists VTCONGVAN;

drop table if exists YEUCAU;

/*==============================================================*/
/* Table: CHATLUONG                                             */
/*==============================================================*/
create table CHATLUONG
(
   CLMA                 char(3) not null,
   CLTEN                varchar(200),
   DAXOA int(2) default 0,
   primary key (CLMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: CHUCDANH                                              */
/*==============================================================*/
create table CHUCDANH
(
   CDMA                 varchar(10) not null,
   CDTEN                varchar(100),
   DAXOA int(2) default 0,
   primary key (CDMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: CONGVAN                                               */
/*==============================================================*/
create table CONGVAN
(
   CVID                 int primary  key auto_increment not null,
--   FILEID               int not null,
   DVMA                 varchar(10) not null,
   TTMA                 varchar(10) not null,
   MDMA                 char(3) not null,
   SODEN                int,
   CVNGAYNHAN           date,
   CVSO                 varchar(20),
   CVNGAYDI             date,
   TRICHYEU             text,
   BUTPHE               text,
   DAXOA int(2) default 0
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8 ;

/*==============================================================*/
/* Table: CTNGUOIDUNG                                           */
/*==============================================================*/
create table CTNGUOIDUNG
(
   MSNV                 varchar(10),
   MATKHAU              varchar(80),
   KHOA int(2) default 0
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: CTVATTU                                               */
/*==============================================================*/
create table CTVATTU
(
	CTVTID int primary key auto_increment,
   NSXMA                char(3) not null,
   CLMA                 char(3) not null,
   VTMA                 char(16) not null,
   DINHMUC              int,
   SOLUONGTON           int,
   DAXOA int(2) default 0,
CONSTRAINT UNIQUE NONCLUSTERED
(
	NSXMA, CLMA, VTMA
)
--   primary key (NSXMA, CLMA, VTMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: DONVI                                                 */
/*==============================================================*/
create table DONVI
(
   DVMA                 varchar(10) not null,
   DVTEN                varchar(300),
   SDT                  varchar(12),
   EMAIL                varchar(300),
   DIACHI               varchar(100),
   DAXOA int(2) default 0,
   primary key (DVMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

create table DONVITINH
(
	DVTID int  primary key auto_increment,
	DVTTEN               varchar(20),
	DAXOA int(2)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
/*==============================================================*/
/* Table: FILE                                                  */
/*==============================================================*/
create table FILE
(
   FILEID int not null,
   CVID  int not null,
   DIACHI               varchar(400),
   MOTA                 text,
   primary key (FILEID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: MUCDICH                                               */
/*==============================================================*/
create table MUCDICH
(
   MDMA                 char(3) not null,
   MDTEN                varchar(200),
   DAXOA int(2) default 0,
   primary key (MDMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: NGUOIDUNG                                             */
/*==============================================================*/
create table NGUOIDUNG
(
   MSNV                 varchar(20) not null,
   CDMA                 varchar(10) not null,
   HOTEN                varchar(50),
   DIACHI               varchar(300),
   EMAIL                varchar(50),
   SDT                  varchar(13),
   primary key (MSNV)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: NOISANXUAT                                            */
/*==============================================================*/
create table NOISANXUAT
(
   NSXMA                char(3) not null,
   NSXTEN               varchar(200),
   DAXOA int(2) default 0,
   primary key (NSXMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: PHIEUNHAP                                             */
/*==============================================================*/
create table PHIEUNHAP
(
   PNID                 int not null,
   CVID                 int not null,
   MSNV                 varchar(10) not null,
   VTID                 int not null,
   PNNGAY               date not null,
   DAXOA int(2) default 0,
   primary key (PNID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: PHIEUXUAT                                             */
/*==============================================================*/
create table PHIEUXUAT
(
   PXID                 int not null,
   CVID                 int not null,
   MSNV                 varchar(10) not null,
   VTID                 int not null,
   PXNGAY               date,
   DAXOA int(2) default 0,
   primary key (PXID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: TRANGTHAI                                             */
/*==============================================================*/
create table TRANGTHAI
(
   TTMA                 varchar(10) not null,
   TTTEN                varchar(20),
   DAXOA int(2) default 0,
   primary key (TTMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VAITRO                                                */
/*==============================================================*/
drop table  IF EXISTS VAITRO;
create table if not EXISTS VAITRO
(
   VTMA   varchar(3) primary key,
   VTTEN                varchar(200),
   DAXOA int(2) default 0
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VATTU                                                 */
/*==============================================================*/
create table VATTU
(
   VTMA                 char(16) not null,
   VTTEN                varchar(200),
   DVTID int,
   DAXOA int(2) default 0,
   primary key (VTMA),
   constraint fk_DVT foreign key(DVTID) references DONVITINH(DVTID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VATTUNHAP                                             */
/*==============================================================*/
create table VATTUNHAP
(
   NSXMA                char(3) not null,
   CLMA                 char(3) not null,
   VTMA                 char(16) not null,
   PNID                 int not null,
   SOLUONG              int,
   DAXOA int(2) default 0,
   primary key (NSXMA, CLMA, VTMA, PNID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VATTUXUAT                                             */
/*==============================================================*/
create table VATTUXUAT
(
   CTVTID int not null,
   CVID                 int not null,
   PXID                 int not null,
   SOLUONG              int,
   DAXOA int(2) default 0,
   primary key (CTVTID, CVID, PXID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VTCONGVAN                                             */
/*==============================================================*/
create table VTCONGVAN
(
   CVID                 int not null,
   MSNV                 varchar(20) not null,
   VTMA                 varchar(3) not null,
   TTMA varchar(10) not null,
   DAXOA int(2) default 0,
   primary key (CVID, MSNV, VTMA),
   constraint fk_ttMa foreign key (TTMA) references TRANGTHAI(TTMA),
   constraint fk_vtMa foreign key (VTMA) references VAITRO(VTMA),
   constraint fk_cvId foreign key (CVID) references CONGVAN(CVID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: YEUCAU                                                */
/*==============================================================*/
create table YEUCAU(
	YCID int primary key auto_increment,
	CTVTID int not null,
	CVID int not null,
	DAXOA int(2) default 0,
	YCSOLUONG int,
	CAPSOLUONG int,
	CONSTRAINT UNIQUE NONCLUSTERED(CVID, CTVTID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE NHATKY (
	NKID int primary key auto_increment,
	MSNV varchar(10),
	HOATDONG TEXT,
	THOIGIAN date,
	NOIDUNG TEXT,
	constraint FK_MSNV foreign key (MSNV) references NGUOIDUNG(MSNV)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table CONGVAN add constraint FK_DONVI_CONGVAN2 foreign key (DVMA)
      references DONVI (DVMA) on delete restrict on update restrict;

-- alter table CONGVAN add constraint FK_FILE_CONGVAN2 foreign key (FILEID)
   -- references FILE (FILEID) on delete restrict on update restrict;
alter table FILE add constraint FK_FILE_CONGVAN2 foreign key (CVID)
      references CONGVAN (CVID) on delete restrict on update restrict;

alter table CONGVAN add constraint FK_MUCDICH2 foreign key (MDMA)
      references MUCDICH (MDMA) on delete restrict on update restrict;

alter table CONGVAN add constraint FK_RELATIONSHIP_11 foreign key (TTMA)
      references TRANGTHAI (TTMA) on delete restrict on update restrict;
-- ---------------------------

alter table CTNGUOIDUNG add constraint FK_CHI_TIET_NGUOIDUNG foreign key (MSNV)
      references NGUOIDUNG (MSNV) on delete restrict on update restrict;

alter table CTVATTU add constraint FK_CHATLUONG_YEUCAU2 foreign key (CLMA)
      references CHATLUONG (CLMA) on delete restrict on update restrict;

alter table CTVATTU add constraint FK_RELATIONSHIP_15 foreign key (VTMA)
      references VATTU (VTMA) on delete restrict on update restrict;

alter table CTVATTU add constraint FK_SANXUAT2 foreign key (NSXMA)
      references NOISANXUAT (NSXMA) on delete restrict on update restrict;

alter table NGUOIDUNG add constraint FK_RELATIONSHIP_2 foreign key (CDMA)
      references CHUCDANH (CDMA) on delete restrict on update restrict;

-- alter table PHIEUNHAP add constraint FK_LAP_PHIEUNHAP foreign key (CVID, MSNV, VTID)
   --   references VTCONGVAN (CVID, MSNV, VTID) on delete restrict on update restrict;

-- alter table PHIEUXUAT add constraint FK_LAP_PHIEU_XUAT foreign key (CVID, MSNV, VTID)
--      references VTCONGVAN (CVID, MSNV, VTID) on delete restrict on update restrict;

-- alter table VATTUNHAP add constraint FK_CHITIET_VATTU_NHAP foreign key (NSXMA, CLMA, VTMA)
 --     references CTVATTU (NSXMA, CLMA, VTMA) on delete restrict on update restrict;

alter table VATTUNHAP add constraint FK_VATTU_NHAP2 foreign key (PNID)
      references PHIEUNHAP (PNID) on delete restrict on update restrict;

alter table VATTUXUAT add constraint FK_RELATIONSHIP_21 foreign key (PXID)
      references PHIEUXUAT (PXID) on delete restrict on update restrict;

-- alter table VATTUXUAT add constraint FK_VATTU_DUOCXUAT foreign key (CTVTID, CVID)
   --   references YEUCAU (CTVTID, CVID) on delete restrict on update restrict;

alter table VTCONGVAN add constraint FK_RELATIONSHIP_14 foreign key (MSNV)
      references NGUOIDUNG (MSNV) on delete restrict on update restrict;

alter table VTCONGVAN add constraint FK_VAI_TRO_NGUOI_XU_LY foreign key (VTID)
      references VAITRO (VTID) on delete restrict on update restrict;

alter table VTCONGVAN add constraint FK_XULY2 foreign key (CVID)
      references CONGVAN (CVID) on delete restrict on update restrict;

alter table YEUCAU add constraint FK_RELATIONSHIP_13 foreign key (CVID)
      references CONGVAN (CVID) on delete restrict on update restrict;

alter table YEUCAU add constraint FK_RELATIONSHIP_7 foreign key (CTVTID)
    references CTVATTU (CTVTID) on delete restrict on update restrict;


-- alter table CTNGUOIDUNG ADD KHOA int(2) default 0;


insert into VAITRO values(1,'Lập phiếu nhập',0);
insert into VAITRO values(2,'Mua vật tư',0);
insert into VAITRO values(3,'Cấp vật tư',0);


insert into CHUCDANH values ('TP', 'Trường phòng',0);
insert into CHUCDANH values ('NV', 'Nhân viên',0);
insert into CHUCDANH values ('TK', 'Thư Ký',0);
insert into CHUCDANH values ('VT', 'Văn thư',0);
insert into CHUCDANH values ('PP', 'Phó phòng',0);
insert into CHUCDANH value('AD','Admin',0);


insert into NGUOIDUNG values ('admin123','AD','Vo Phu Quoi','An Giang','quoipro94@gmail.com','0979921380');

-- alter table VAITRO add DAXOA int(2) not null;
-- alter table CHUCDANH add DAXOA int(2) not null;
-- alter table NOISANXUAT add DAXOA int(2) not null;
-- alter table MUCDICH add DAXOA int(2) not null;
-- alter table VATTU add DAXOA int(2) not null;
-- alter table CTVATTU add DAXOA int(2) not null;
-- alter table CHATLUONG add DAXOA int(2) not null;
-- alter table DONVITINH add DAXOA int(2) not null;
-- alter table DONVI add DAXOA int(2) not null;

-- alter table CTNGUOIDUNG ADD KHOA int(2) default 0;

-- update DONVI set DAXOA = 0;
-- update CHATLUONG set DAXOA = 0;
-- update VAITRO set DAXOA = 0;
-- update CHUCDANH set DAXOA = 0;
-- update NOISANXUAT set DAXOA = 0;
-- update MUCDICH set DAXOA = 0;
-- update VATTU set DAXOA = 0;
-- update CTVATTU set DAXOA = 0;
-- update CHATLUONG set DAXOA = 0;
-- update DONVITINH set DAXOA = 0;

INSERT INTO TRANGTHAI VALUES('CGQ','Chưa giải quyết', 0);
INSERT INTO TRANGTHAI VALUES('DGQ','Đang giải quyết', 0);
INSERT INTO TRANGTHAI VALUES('DaGQ','Đã giải quyết', 0);
-- ---------------------------

