
/* Drop Tables */

DROP TABLE IF EXISTS trade_strategy_cards;
DROP TABLE IF EXISTS trade_strategy_palettes;
DROP TABLE IF EXISTS trade_rules;
DROP TABLE IF EXISTS trade_strategies;
DROP TABLE IF EXISTS analysis_brand_groups;
DROP TABLE IF EXISTS analysis_histories;
DROP TABLE IF EXISTS stocks;
DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS screening;
DROP TABLE IF EXISTS users;




/* Create Tables */

-- 分析銘柄グループ
CREATE TABLE analysis_brand_groups
(
	-- 分析銘柄ID
	gid serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- ラベル
	label varchar NOT NULL,
	-- 分析対象銘柄
	brands text NOT NULL,
	PRIMARY KEY (gid)
) WITHOUT OIDS;


ALTER SEQUENCE analysis_brand_groups_gid_SEQ RESTART 100;


-- 分析履歴
CREATE TABLE analysis_histories
(
	-- ID
	id serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- お気に入りフラグ
	star boolean DEFAULT 'false' NOT NULL,
	-- 総資産
	total_asset int NOT NULL,
	-- 取引戦略バイナリ
	trade_rule_binary bytea NOT NULL,
	-- 資産推移バイナリ
	asset_binary bytea NOT NULL,
	-- 分析種別 : true: 固定テスト
	-- false: 最適化テスト
	fixed_or_optimized boolean NOT NULL,
	-- 保有銘柄バイナリ
	holding_brands_binary bytea,
	-- 分析日時
	analyzed_at date NOT NULL,
	-- コメント
	memo text,
	PRIMARY KEY (id)
) WITHOUT OIDS;


ALTER SEQUENCE analysis_histories_id_SEQ RESTART 100;


-- 銘柄
CREATE TABLE brands
(
	-- 銘柄コード
	code int NOT NULL,
	-- 銘柄名
	name varchar NOT NULL,
	-- 市場
	market varchar NOT NULL,
	-- 廃止フラグ : true: 有効
	-- false: 無効
	enabled boolean DEFAULT 'false' NOT NULL,
	-- 詳細
	detail text NOT NULL,
	PRIMARY KEY (code)
) WITHOUT OIDS;


-- スクリーニング
CREATE TABLE screening
(
	-- ID
	id serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- ラベル
	label varchar NOT NULL,
	-- スクリーニング条件
	condition text NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


ALTER SEQUENCE screening_id_SEQ RESTART 100;


-- 株価
CREATE TABLE stocks
(
	-- 銘柄コード
	code int NOT NULL,
	-- 登録日
	created_at date NOT NULL,
	-- 高値
	high_price numeric NOT NULL,
	-- 安値
	low_price numeric NOT NULL,
	-- 始値
	open_price numeric NOT NULL,
	-- 終値
	close_price numeric NOT NULL,
	-- 出来高
	volume int NOT NULL,
	-- ストップ安フラグ
	stop_low boolean DEFAULT 'false' NOT NULL,
	-- ストップ高フラグ
	stop_high boolean DEFAULT 'false' NOT NULL,
	-- 更新日
	updated_at date,
	UNIQUE (code, created_at)
) WITHOUT OIDS;


-- 取引ルール
CREATE TABLE trade_rules
(
	-- 取引ルールID
	rid serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- 取引戦略ID
	sid int NOT NULL,
	-- 当日取引フラグ : true: 当日
	-- false: 明日
	today_or_tomorrow boolean NOT NULL,
	-- 売買フラグ : true: 購入
	-- false: 売却
	buy_or_sell boolean NOT NULL,
	-- 取引タイミング種別 : 1: 寄成
	-- 2: 引成
	-- 3: 成行
	-- 4: 指値
	trade_timing_type int NOT NULL,
	-- 指値
	limit_order_value numeric,
	-- 仕掛けフラグ : true: 仕掛け
	-- false: 手仕舞い
	in_or_exit boolean NOT NULL,
	-- 順序
	order_by int NOT NULL,
	PRIMARY KEY (rid),
	UNIQUE (sid, in_or_exit, order_by)
) WITHOUT OIDS;


ALTER SEQUENCE trade_rules_rid_SEQ RESTART 100;


-- 取引戦略 : 取引戦略を格納するテーブルです。
CREATE TABLE trade_strategies
(
	-- 取引戦略ID
	sid serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- 分析銘柄グループID
	gid int,
	-- ラベル
	label varchar NOT NULL,
	-- 分析開始日
	analysis_start_date date NOT NULL,
	-- 分析終了日
	analysis_end_date date NOT NULL,
	-- 最終分析日
	analyzed_at date,
	-- コメント
	memo text,
	PRIMARY KEY (sid)
) WITHOUT OIDS;


ALTER SEQUENCE trade_strategies_sid_SEQ RESTART 100;


-- 取引戦略カード
CREATE TABLE trade_strategy_cards
(
	-- 取引戦略カードID
	cid serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- 取引戦略パレットID
	pid int,
	-- 取引戦略ID
	sid int NOT NULL,
	-- ラベル
	label varchar NOT NULL,
	-- 利用フラグ
	used boolean NOT NULL,
	-- カード種別 : 1: 比較
	-- 2: 交差
	-- 3: 時間
	card_type int NOT NULL,
	-- 左辺指標種別
	left_side_indicator_type int,
	-- 左辺日数
	left_side_days int,
	-- 右辺指標種別
	right_side_indicator_type int,
	-- 右辺日数
	right_side_days int,
	-- 右辺種別フラグ : true: fix
	-- false: flex
	right_side_fix_or_flex boolean,
	-- 右辺固定値
	right_side_fix_value numeric,
	-- 係数
	coefficient numeric,
	-- 比較種別 : 1: >
	-- 2: >=
	-- 3: <
	-- 4: <=
	-- 5: =
	comparison_type int,
	-- 交差種別 : 1: 上抜け
	-- 2: 下抜け
	cross_type int,
	-- 経過日数
	elapsed_day int,
	PRIMARY KEY (cid),
	UNIQUE (uid, pid)
) WITHOUT OIDS;


ALTER SEQUENCE trade_strategy_cards_cid_SEQ RESTART 100;


-- 取引戦略パレット
CREATE TABLE trade_strategy_palettes
(
	-- 取引戦略パレットID
	pid serial NOT NULL,
	-- ユーザID
	uid int NOT NULL,
	-- 取引ルールID
	rid int NOT NULL,
	-- 左結合種別 : 0: なし
	-- 1: AND
	-- 2: OR
	left_joint_type int NOT NULL,
	-- 右結合種別 : 0: なし
	-- 1: AND
	-- 2: OR
	right_joint_type int NOT NULL,
	-- ネスト開始フラグ : true: ネスト開く
	-- false: 何もしない
	nest_open boolean NOT NULL,
	-- ネスト終了フラグ : true: ネスト閉じる
	-- false: 何もしない
	nest_close boolean NOT NULL,
	-- 順序
	order_by int NOT NULL,
	PRIMARY KEY (pid),
	UNIQUE (uid, rid, order_by)
) WITHOUT OIDS;


ALTER SEQUENCE trade_strategy_palettes_pid_SEQ RESTART 100;


-- ユーザ
CREATE TABLE users
(
	-- ユーザID
	uid serial NOT NULL,
	-- 氏名
	name varchar NOT NULL,
	-- パスワード
	password varchar NOT NULL,
	-- 権限
	authority varchar NOT NULL,
	PRIMARY KEY (uid)
) WITHOUT OIDS;


ALTER SEQUENCE users_uid_SEQ RESTART 100;



/* Create Foreign Keys */

ALTER TABLE trade_strategies
	ADD FOREIGN KEY (gid)
	REFERENCES analysis_brand_groups (gid)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;


ALTER TABLE stocks
	ADD FOREIGN KEY (code)
	REFERENCES brands (code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE trade_strategy_palettes
	ADD FOREIGN KEY (rid)
	REFERENCES trade_rules (rid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_rules
	ADD FOREIGN KEY (sid)
	REFERENCES trade_strategies (sid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_strategy_cards
	ADD FOREIGN KEY (sid)
	REFERENCES trade_strategies (sid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_strategy_cards
	ADD FOREIGN KEY (pid)
	REFERENCES trade_strategy_palettes (pid)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;


ALTER TABLE analysis_brand_groups
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE analysis_histories
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE screening
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_rules
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_strategies
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_strategy_cards
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE trade_strategy_palettes
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;



/* Comments */

COMMENT ON TABLE analysis_brand_groups IS '分析銘柄グループ';
COMMENT ON COLUMN analysis_brand_groups.gid IS '分析銘柄ID';
COMMENT ON COLUMN analysis_brand_groups.uid IS 'ユーザID';
COMMENT ON COLUMN analysis_brand_groups.label IS 'ラベル';
COMMENT ON COLUMN analysis_brand_groups.brands IS '分析対象銘柄';
COMMENT ON TABLE analysis_histories IS '分析履歴';
COMMENT ON COLUMN analysis_histories.id IS 'ID';
COMMENT ON COLUMN analysis_histories.uid IS 'ユーザID';
COMMENT ON COLUMN analysis_histories.star IS 'お気に入りフラグ';
COMMENT ON COLUMN analysis_histories.total_asset IS '総資産';
COMMENT ON COLUMN analysis_histories.trade_rule_binary IS '取引戦略バイナリ';
COMMENT ON COLUMN analysis_histories.asset_binary IS '資産推移バイナリ';
COMMENT ON COLUMN analysis_histories.fixed_or_optimized IS '分析種別 : true: 固定テスト
false: 最適化テスト';
COMMENT ON COLUMN analysis_histories.holding_brands_binary IS '保有銘柄バイナリ';
COMMENT ON COLUMN analysis_histories.analyzed_at IS '分析日時';
COMMENT ON COLUMN analysis_histories.memo IS 'コメント';
COMMENT ON TABLE brands IS '銘柄';
COMMENT ON COLUMN brands.code IS '銘柄コード';
COMMENT ON COLUMN brands.name IS '銘柄名';
COMMENT ON COLUMN brands.market IS '市場';
COMMENT ON COLUMN brands.enabled IS '廃止フラグ : true: 有効
false: 無効';
COMMENT ON COLUMN brands.detail IS '詳細';
COMMENT ON TABLE screening IS 'スクリーニング';
COMMENT ON COLUMN screening.id IS 'ID';
COMMENT ON COLUMN screening.uid IS 'ユーザID';
COMMENT ON COLUMN screening.label IS 'ラベル';
COMMENT ON COLUMN screening.condition IS 'スクリーニング条件';
COMMENT ON TABLE stocks IS '株価';
COMMENT ON COLUMN stocks.code IS '銘柄コード';
COMMENT ON COLUMN stocks.created_at IS '登録日';
COMMENT ON COLUMN stocks.high_price IS '高値';
COMMENT ON COLUMN stocks.low_price IS '安値';
COMMENT ON COLUMN stocks.open_price IS '始値';
COMMENT ON COLUMN stocks.close_price IS '終値';
COMMENT ON COLUMN stocks.volume IS '出来高';
COMMENT ON COLUMN stocks.stop_low IS 'ストップ安フラグ';
COMMENT ON COLUMN stocks.stop_high IS 'ストップ高フラグ';
COMMENT ON COLUMN stocks.updated_at IS '更新日';
COMMENT ON TABLE trade_rules IS '取引ルール';
COMMENT ON COLUMN trade_rules.rid IS '取引ルールID';
COMMENT ON COLUMN trade_rules.uid IS 'ユーザID';
COMMENT ON COLUMN trade_rules.sid IS '取引戦略ID';
COMMENT ON COLUMN trade_rules.today_or_tomorrow IS '当日取引フラグ : true: 当日
false: 明日';
COMMENT ON COLUMN trade_rules.buy_or_sell IS '売買フラグ : true: 購入
false: 売却';
COMMENT ON COLUMN trade_rules.trade_timing_type IS '取引タイミング種別 : 1: 寄成
2: 引成
3: 成行
4: 指値';
COMMENT ON COLUMN trade_rules.limit_order_value IS '指値';
COMMENT ON COLUMN trade_rules.in_or_exit IS '仕掛けフラグ : true: 仕掛け
false: 手仕舞い';
COMMENT ON COLUMN trade_rules.order_by IS '順序';
COMMENT ON TABLE trade_strategies IS '取引戦略 : 取引戦略を格納するテーブルです。';
COMMENT ON COLUMN trade_strategies.sid IS '取引戦略ID';
COMMENT ON COLUMN trade_strategies.uid IS 'ユーザID';
COMMENT ON COLUMN trade_strategies.gid IS '分析銘柄グループID';
COMMENT ON COLUMN trade_strategies.label IS 'ラベル';
COMMENT ON COLUMN trade_strategies.analysis_start_date IS '分析開始日';
COMMENT ON COLUMN trade_strategies.analysis_end_date IS '分析終了日';
COMMENT ON COLUMN trade_strategies.analyzed_at IS '最終分析日';
COMMENT ON COLUMN trade_strategies.memo IS 'コメント';
COMMENT ON TABLE trade_strategy_cards IS '取引戦略カード';
COMMENT ON COLUMN trade_strategy_cards.cid IS '取引戦略カードID';
COMMENT ON COLUMN trade_strategy_cards.uid IS 'ユーザID';
COMMENT ON COLUMN trade_strategy_cards.pid IS '取引戦略パレットID';
COMMENT ON COLUMN trade_strategy_cards.sid IS '取引戦略ID';
COMMENT ON COLUMN trade_strategy_cards.label IS 'ラベル';
COMMENT ON COLUMN trade_strategy_cards.used IS '利用フラグ';
COMMENT ON COLUMN trade_strategy_cards.card_type IS 'カード種別 : 1: 比較
2: 交差
3: 時間';
COMMENT ON COLUMN trade_strategy_cards.left_side_indicator_type IS '左辺指標種別';
COMMENT ON COLUMN trade_strategy_cards.left_side_days IS '左辺日数';
COMMENT ON COLUMN trade_strategy_cards.right_side_indicator_type IS '右辺指標種別';
COMMENT ON COLUMN trade_strategy_cards.right_side_days IS '右辺日数';
COMMENT ON COLUMN trade_strategy_cards.right_side_fix_or_flex IS '右辺種別フラグ : true: fix
false: flex';
COMMENT ON COLUMN trade_strategy_cards.right_side_fix_value IS '右辺固定値';
COMMENT ON COLUMN trade_strategy_cards.coefficient IS '係数';
COMMENT ON COLUMN trade_strategy_cards.comparison_type IS '比較種別 : 1: >
2: >=
3: <
4: <=
5: =';
COMMENT ON COLUMN trade_strategy_cards.cross_type IS '交差種別 : 1: 上抜け
2: 下抜け';
COMMENT ON COLUMN trade_strategy_cards.elapsed_day IS '経過日数';
COMMENT ON TABLE trade_strategy_palettes IS '取引戦略パレット';
COMMENT ON COLUMN trade_strategy_palettes.pid IS '取引戦略パレットID';
COMMENT ON COLUMN trade_strategy_palettes.uid IS 'ユーザID';
COMMENT ON COLUMN trade_strategy_palettes.rid IS '取引ルールID';
COMMENT ON COLUMN trade_strategy_palettes.left_joint_type IS '左結合種別 : 0: なし
1: AND
2: OR';
COMMENT ON COLUMN trade_strategy_palettes.right_joint_type IS '右結合種別 : 0: なし
1: AND
2: OR';
COMMENT ON COLUMN trade_strategy_palettes.nest_open IS 'ネスト開始フラグ : true: ネスト開く
false: 何もしない';
COMMENT ON COLUMN trade_strategy_palettes.nest_close IS 'ネスト終了フラグ : true: ネスト閉じる
false: 何もしない';
COMMENT ON COLUMN trade_strategy_palettes.order_by IS '順序';
COMMENT ON TABLE users IS 'ユーザ';
COMMENT ON COLUMN users.uid IS 'ユーザID';
COMMENT ON COLUMN users.name IS '氏名';
COMMENT ON COLUMN users.password IS 'パスワード';
COMMENT ON COLUMN users.authority IS '権限';



