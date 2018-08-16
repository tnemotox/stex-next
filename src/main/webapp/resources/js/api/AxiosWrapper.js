/**
 * AxiosWrapper
 *
 * JSON REST APIアクセスのためのAxiosラッパーです。
 */
import axios from 'axios';
import { Notification } from 'element-ui';

const AxiosWrapper = axios.create({
  withCredentials: true,
  baseURL: '/'
});

// axiosの要求に対する共通的な処理を設定する
AxiosWrapper.interceptors.request.use(
  // 正常系の共通処理
  request => {
    // トーストを削除
    Notification.closeAll()
    // IEが同じURLにリクエストしたときにキャッシュした値を返却してしまうため、アクセスした時刻をクエリストリングに付与する
    const queryStrings = request.url.split('?');
    if (queryStrings.length >= 2) {
      request.url += `&_=${Date.now()}`;
    }
    else {
      request.url += `?_=${Date.now()}`;
    }
    return request;
  }
);

// axiosの応答に対する共通的な処理を設定する
AxiosWrapper.interceptors.response.use(
  // 正常系の共通処理
  response => {
    return response;
  },
  // 異常系の共通処理
  error => {
    try {
      const statusCode = error.response.status;
      // ステータスコードが500の場合はシステムエラー画面に遷移
      if (statusCode === 500) {
        window.location.href = '/error';
      }
      // ステータスコードが400の場合はエラーメッセージをトーストで出力
      else if (statusCode >= 400) {
        Object.keys(error.response.data).forEach(async code => {
          const messageInfo = error.response.data[code]
          await new Promise(resolve => setTimeout(resolve, 1))
          switch(messageInfo.level.toLowerCase()) {
            case 'warning':
              Notification.warning({message: messageInfo.message, duration: 0, position: 'bottom-right'})
              break
            case 'info':
              Notification.info({message: messageInfo.message, position: 'bottom-right'})
              break
            case 'error':
              Notification.error({message: messageInfo.message, duration: 0, position: 'bottom-right'})
              break
            default:
              break;
          }
        })
      }
    }
    catch (e) {
      // エラー処理を実装する
      console.log(e);
    }
    return Promise.reject(error);
  }
);

export default AxiosWrapper;
