DROP DATABASE kanpian;
CREATE DATABASE kanpian
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Chinese (Simplified)_People''s Republic of China.936'
       LC_CTYPE = 'Chinese (Simplified)_People''s Republic of China.936'
       CONNECTION LIMIT = -1;

-- ----------------------------
-- Table structure for errpage
-- ----------------------------
DROP TABLE IF EXISTS "public"."errpage";
CREATE TABLE "public"."errpage" (
"id" varchar(30) COLLATE "default" NOT NULL,
"type" varchar(30) COLLATE "default",
"num" varchar(30) COLLATE "default",
"errmsg" text COLLATE "default",
"searchkey" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for javsrc
-- ----------------------------
DROP TABLE IF EXISTS "public"."javsrc";
CREATE TABLE "public"."javsrc" (
"id" varchar(30) COLLATE "default" NOT NULL,
"title" text COLLATE "default",
"times" varchar(30) COLLATE "default",
"imgsrc" text COLLATE "default",
"tabtype" varchar(30) COLLATE "default",
"isdown" varchar(1) COLLATE "default",
"tags" text COLLATE "default",
"btfile" text COLLATE "default",
"btname" text COLLATE "default",
"isstar" varchar(1) COLLATE "default",
"sbm" text COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table errpage
-- ----------------------------
ALTER TABLE "public"."errpage" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table javsrc
-- ----------------------------
CREATE INDEX "id" ON "public"."javsrc" USING btree ("id");
CREATE INDEX "javsrc_title_tags_idx" ON "public"."javsrc" USING btree ("title", "tags");
CREATE INDEX "sbm" ON "public"."javsrc" USING hash ("sbm");
CREATE INDEX "times" ON "public"."javsrc" USING hash ("times");

-- ----------------------------
-- Primary Key structure for table javsrc
-- ----------------------------
ALTER TABLE "public"."javsrc" ADD PRIMARY KEY ("id");
