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

	drop table if exists DONVITINH;

	drop table if exists VATTU;

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
	   CLTEN                varchar(100),
	   primary key (CLMA)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: CHUCDANH                                              */
	/*==============================================================*/
	create table CHUCDANH
	(
	   CDMA                 varchar(10) not null,
	   CDTEN                varchar(30),
	   primary key (CDMA)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: CONGVAN                                               */
	/*==============================================================*/
	create table CONGVAN
	(
	   CVID                 int not null,
	--   FILEID               int not null,
	   DVMA                 varchar(10) not null,
	   TTMA                 varchar(10) not null,
	   MDMA                 char(3) not null,
	   SODEN                int,
	   CVNGAYNHAN           date,
	   CVSO                 varchar(10),
	   CVNGAYDI             date,
	   TRICHYEU             text,
	   BUTPHE               text,
	   DAXOA int,
	   primary key (CVID)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: CTNGUOIDUNG                                           */
	/*==============================================================*/
	create table CTNGUOIDUNG
	(
	   MSNV                 varchar(10),
	   MATKHAU              varchar(40)
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
	   DVTEN                varchar(30),
	   SDT                  varchar(12),
	   EMAIL                varchar(50),
	   DIACHI               varchar(100),
	   primary key (DVMA)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: FILE                                                  */
	/*==============================================================*/
	create table FILE
	(
	   FILEID int not null,
	   CVID  int not null,
	   DIACHI               varchar(100),
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
	   MDTEN                varchar(50),
	   primary key (MDMA)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: NGUOIDUNG                                             */
	/*==============================================================*/
	create table NGUOIDUNG
	(
	   MSNV                 varchar(10) not null,
	   CDMA                 varchar(10) not null,
	   HOTEN                varchar(50),
	   DIACHI               varchar(100),
	   EMAIL                varchar(50),
	   SDT                  varchar(12),
	   primary key (MSNV)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: NOISANXUAT                                            */
	/*==============================================================*/
	create table NOISANXUAT
	(
	   NSXMA                char(3) not null,
	   NSXTEN               varchar(20),
       DAXOA int(2),
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
	   primary key (TTMA)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: VAITRO                                                */
	/*==============================================================*/
	create table VAITRO
	(
	   VTID                 int not null,
	   VTTEN                varchar(50),
	   primary key (VTID)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: DONVITINH                                                */
	/*==============================================================*/
	create table DONVITINH
	(
		DVTID int  primary key auto_increment,
	   DVTTEN               varchar(20),
	   DAXOA int(2)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;
	/*==============================================================*/
	/* Table: VATTU                                                 */
	/*==============================================================*/
	create table VATTU
	(
	   VTMA                 char(16) not null,
	   VTTEN                varchar(50),
	   DVTID                int,
	   DAXOA int(2),
	   primary key (VTMA),
	   constraint fk_DVT foreign key (DVTID) references DONVITINH(DVTID)
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
	   primary key (CTVTID, CVID, PXID)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: VTCONGVAN                                             */
	/*==============================================================*/
	create table VTCONGVAN
	(
	   CVID                 int not null,
	   MSNV                 varchar(10) not null,
	   VTID                 int not null,
	   primary key (CVID, MSNV, VTID)
	) ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

	/*==============================================================*/
	/* Table: YEUCAU                                                */
	/*==============================================================*/
create table YEUCAU(
	YCID int primary key auto_increment,
	CTVTID int not null,
	CVID int not null,
	DAXOA int(2),
	YCSOLUONG int,
	CAPSOLUONG int,
	CONSTRAINT UNIQUE NONCLUSTERED(CVID, CTVTID)
) ENGINE = InnoDB
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

		  alter table VATTU add constraint FK_DONVITINH foreign key (DVTID)
		  references DONVITINH (DVTID) on delete restrict on update restrict;
		  
	alter table CTVATTU add constraint FK_RELATIONSHIP_15 foreign key (VTMA)
		  references VATTU (VTMA) on delete restrict on update restrict;

	alter table CTVATTU add constraint FK_SANXUAT2 foreign key (NSXMA)
		  references NOISANXUAT (NSXMA) on delete restrict on update restrict;

	alter table NGUOIDUNG add constraint FK_RELATIONSHIP_2 foreign key (CDMA)
		  references CHUCDANH (CDMA) on delete restrict on update restrict;

	--      references VTCONGVAN (CVID, MSNV, VTID) on delete restrict on update restrict;

	-- alter table PHIEUXUAT add constraint FK_LAP_PHIEU_XUAT foreign key (CVID, MSNV, VTID)
	 --     references VTCONGVAN (CVID, MSNV, VTID) on delete restrict on update restrict;

	-- alter table VATTUNHAP add constraint FK_CHITIET_VATTU_NHAP foreign key (NSXMA, CLMA, VTMA)
	   --   references CTVATTU (NSXMA, CLMA, VTMA) on delete restrict on update restrict;

	alter table VATTUNHAP add constraint FK_VATTU_NHAP2 foreign key (PNID)
		  references PHIEUNHAP (PNID) on delete restrict on update restrict;

	alter table VATTUXUAT add constraint FK_RELATIONSHIP_21 foreign key (PXID)
		  references PHIEUXUAT (PXID) on delete restrict on update restrict;

	alter table VATTUXUAT add constraint FK_VATTU_DUOCXUAT foreign key (CTVTID, CVID)
		  references YEUCAU (CTVTID, CVID) on delete restrict on update restrict;

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
	insert into TRANGTHAI values('DGQ','Dang giai quyet');
<<<<<<< HEAD

insert into VAITRO values(1,'Lap phieu nhap');
insert into VAITRO values(2,'Mua vat tu');
insert into VAITRO values(3,'Cap vat tu');
insert into VAITRO values(4,'Mua Lap phieu xuat');
insert into VAITRO values(5,'Mua Lap phieu xuat');
insert into VAITRO values(6,'Mua Lap phieu xuat');
insert into VAITRO values(7,'Mua Lap phieu xuat');
insert into VAITRO values(8,'Mua Lap phieu xuat');

insert into CHUCDANH values ('GD', 'Giam doc');
insert into CHUCDANH values ('GD1', 'Giam doc');
insert into CHUCDANH values ('GD2', 'Giam doc');
insert into CHUCDANH values ('GD3', 'Giam doc');
insert into CHUCDANH values ('TP', 'Truong phong');
insert into CHUCDANH values ('NV', 'Nhan vien');
insert into CHUCDANH values ('TK', 'Thu ky');
insert into CHUCDANH values ('VT', 'Van thu');
=======
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
   CLTEN                varchar(20),
   primary key (CLMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: CHUCDANH                                              */
/*==============================================================*/
create table CHUCDANH
(
   CDMA                 varchar(10) not null,
   CDTEN                varchar(30),
   primary key (CDMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: CONGVAN                                               */
/*==============================================================*/
create table CONGVAN
(
   CVID                 int not null,
--   FILEID               int not null,
   DVMA                 varchar(10) not null,
   TTMA                 varchar(10) not null,
   MDMA                 char(3) not null,
   SODEN                int,
   CVNGAYNHAN           date,
   CVSO                 varchar(10),
   CVNGAYDI             date,
   TRICHYEU             text,
   BUTPHE               text,
   DAXOA int,
   primary key (CVID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: CTNGUOIDUNG                                           */
/*==============================================================*/
create table CTNGUOIDUNG
(
   MSNV                 varchar(10),
   MATKHAU              varchar(40)
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
   DVTEN                varchar(30),
   SDT                  varchar(12),
   EMAIL                varchar(50),
   DIACHI               varchar(100),
   primary key (DVMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: FILE                                                  */
/*==============================================================*/
create table FILE
(
   FILEID int not null,
   CVID  int not null,
   DIACHI               varchar(100),
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
   MDTEN                varchar(50),
   primary key (MDMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: NGUOIDUNG                                             */
/*==============================================================*/
create table NGUOIDUNG
(
   MSNV                 varchar(10) not null,
   CDMA                 varchar(10) not null,
   HOTEN                varchar(50),
   DIACHI               varchar(100),
   EMAIL                varchar(50),
   SDT                  varchar(12),
   primary key (MSNV)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: NOISANXUAT                                            */
/*==============================================================*/
create table NOISANXUAT
(
   NSXMA                char(3) not null,
   NSXTEN               varchar(20),
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
   primary key (TTMA)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VAITRO                                                */
/*==============================================================*/
create table VAITRO
(
   VTID                 int not null,
   VTTEN                varchar(50),
   primary key (VTID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VATTU                                                 */
/*==============================================================*/
create table VATTU
(
   VTMA                 char(16) not null,
   VTTEN                varchar(50),
   DVT                  varchar(10),
   primary key (VTMA)
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
   primary key (CTVTID, CVID, PXID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: VTCONGVAN                                             */
/*==============================================================*/
create table VTCONGVAN
(
   CVID                 int not null,
   MSNV                 varchar(10) not null,
   VTID                 int not null,
   primary key (CVID, MSNV, VTID)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

/*==============================================================*/
/* Table: YEUCAU                                                */
/*==============================================================*/
create table YEUCAU
(
-- IDYEUCAU int primary key,
--   NSXMA                char(3) not null,
--   CLMA                 char(3) not null,
--   VTMA                 char(16) not null,
	YCID int primary key auto_increment,
	CTVTID int not null,
   CVID                 int not null,
   DAXOA                int,
   YCSOLUONG            int,
   CAPSOLUONG int,
   CONSTRAINT UNIQUE NONCLUSTERED
(
	CVID, CTVTID
)

--   primary key (CTVTID, CVID)
) ENGINE = InnoDB
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

alter table VATTUXUAT add constraint FK_VATTU_DUOCXUAT foreign key (CTVTID, CVID)
      references YEUCAU (CTVTID, CVID) on delete restrict on update restrict;

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
insert into TRANGTHAI values('DGQ','Dang giai quyet');
insert into TRANGTHAI values('DaGQ','Da giai quyet');
insert into TRANGTHAI values('CGQ','Chua giai quyet');

insert into VAITRO values(1,'Lap phieu nhap', 0);
insert into VAITRO values(2,'Mua vat tu', 0);
insert into VAITRO values(3,'Cap vat tu', 0);
insert into VAITRO values(4,'Lap phieu xuat', 0);
insert into VAITRO values(5,'Nhap vat tu', 0);


	insert into CHUCDANH values ('GD', 'Giam doc', 0);
	insert into CHUCDANH values ('GD1', 'Giam doc', 0);
	insert into CHUCDANH values ('GD2', 'Giam doc', 0);
	insert into CHUCDANH values ('GD3', 'Giam doc', 0);
	insert into CHUCDANH values ('TP', 'Truong phong', 0);
	insert into CHUCDANH values ('NV', 'Nhan vien', 0);
	insert into CHUCDANH values ('TK', 'Thu ky', 0);
	insert into CHUCDANH values ('VT', 'Van thu', 0);
>>>>>>> e0abe376de28b5ffd1b0577a294d9994bc1ffe19


insert into NGUOIDUNG values ('b1203959', 'GD', 'Vo Phu Quoi', 'An giang', 'quoipro94@gmail.com', '0979921380');
insert into NGUOIDUNG values ('b1203958', 'TK', 'Le Thi Cam Tien', 'An giang', 'quoipro94@gmail.com', '0979921380');
insert into NGUOIDUNG values ('b1203957', 'TP', 'Nguyen Thi Cam Nhung', 'An giang', 'quoipro94@gmail.com', '0979921380');
insert into NGUOIDUNG values ('b1203955', 'VT', 'Truong Trung Hieu', 'An giang', 'quoipro94@gmail.com', '0979921380');
insert into NGUOIDUNG values ('b1203954', 'NV', 'Truong Quoc huy', 'An giang', 'quoipro94@gmail.com', '0979921380');


insert into CHUCDANH value('AD','Admin', 0);
insert into NGUOIDUNG values ('admin123','Ad','Vo Phu Quoi','An Giang','quoipro94@gmail.com','0979921380');
insert into CTNGUOIDUNG values ('admin123' ,md5('123456789'));

alter table VAITRO add DAXOA int(2) not null;
alter table CHUCDANH add DAXOA int(2) not null;
alter table NOISANXUAT add DAXOA int(2) not null;
alter table MUCDICH add DAXOA int(2) not null;
alter table VATTU add DAXOA int(2) not null;
alter table CTVATTU add DAXOA int(2) not null;
alter table CHATLUONG add DAXOA int(2) not null;
alter table DONVITINH add DAXOA int(2) not null;
<<<<<<< HEAD
=======
alter table DONVI add DAXOA int(2) not null;
>>>>>>> e0abe376de28b5ffd1b0577a294d9994bc1ffe19

update VAITRO set DAXOA = 0;
update CHUCDANH set DAXOA = 0;
update NOISANXUAT set DAXOA = 0;
update MUCDICH set DAXOA = 0;
update VATTU set DAXOA = 0;
update CTVATTU set DAXOA = 0;
update CHATLUONG set DAXOA = 0;
update DONVITINH set DAXOA = 0;

<<<<<<< HEAD
=======
INSERT INTO TRANGTHAI VALUES('CGQ','Chưa giải quyết');
INSERT INTO TRANGTHAI VALUES('DGQ','Dang giải quyết');
INSERT INTO TRANGTHAI VALUES('DaGQ','Đã giải quyết');
-- ---------------------------
INSERT INTO DONVI VALUES('F02F09','Công ty Điện lực Cần Thơ', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
INSERT INTO DONVI VALUES('F09A01','Ban QLDA lưới điện', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
INSERT INTO DONVI VALUES('F09D01','Phòng tổ chức và nhân sự', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
INSERT INTO DONVI VALUES('F02D08','Phòng Kế Hoạch(Cty)', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
INSERT INTO DONVI VALUES('F09D02','Phòng Thanh tra bảo vệ-Pháp', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
INSERT INTO DONVI VALUES('F09D03','Phòng Kỹ thuật Sản xuất(Cty)', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
INSERT INTO DONVI VALUES('F09D04','Phòng Vật tư(Cty)', '0979921380' , 'vpdtevn@gmail.com', 'Can Tho', 0);
-----------------------------
INSERT INTO MUCDICH VALUES('MD1','Mục đích 1', 0);
INSERT INTO MUCDICH VALUES('MD2','Mục đích 2', 0);
INSERT INTO MUCDICH VALUES('MD3','Mục đích 3', 0);
INSERT INTO MUCDICH VALUES('MD4','Mục đích 4', 0);
INSERT INTO MUCDICH VALUES('MD5','Mục đích 5', 0);
INSERT INTO MUCDICH VALUES('MD6','Mục đích 6', 0);
-- ---------------------------
>>>>>>> e0abe376de28b5ffd1b0577a294d9994bc1ffe19

