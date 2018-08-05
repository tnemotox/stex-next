/**
 * APIClient
 *
 * APIエンドポイントのクライアントユーティリティです。
 */
import AxiosWrapper from './AxiosWrapper';

/**
 * APIを利用するための基底クラスです。
 */
export default class {

  /**
   * リクエストパスのリソース、権限を設定する
   *
   * @param resource リソース名を指定
   * @param api APIの識別子
   */
  constructor(resource = '/',  api = '/api') {
    this.resource = resource;
    this.api = api;
  }

  /**
   * 各HTTPメソッドから呼び出されるAPI呼び出し用の関数です。
   * @param type HTTPメソッド名（初期値: "get"）
   * @param path アクセスパス（初期値: ""）
   * @param body リクエストボディ（初期値: null）
   */
  async ajax({ type = 'get', path = '', body = null }) {
    return await AxiosWrapper[type](this.api + this.resource + path, body);
  }

  /**
   * GETでAPIを呼び出すための関数です。
   * @param path アクセスパス（初期値: ""）
   */
  async get(path = '') {
    return await this.ajax({ path });
  }

  /**
   * POSTでAPIを呼び出すための関数です。
   * @param body リクエストボディ
   * @param path アクセスパス（初期値: ""）
   */
  async post(body, path = '') {
    return await this.ajax({ type: 'post', body, path });
  }

  /**
   * DELETEでAPIを呼び出すための関数です。
   * @param path アクセスパス（初期値: ""）
   */
  async delete(path = '') {
    return await this.ajax({ type: 'delete', path });
  }

  /**
   * PUTでAPIを呼び出すための関数です。
   * @param body リクエストボディ
   * @param path アクセスパス（初期値: ""）
   */
  async put(body, path = '') {
    return await this.ajax({ type: 'put', body, path });
  }
}
