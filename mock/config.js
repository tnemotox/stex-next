const jsonServer = require('json-server');
const server = jsonServer.create();
const middlewares = jsonServer.defaults();
const port = 8082;
const prefix = '/api';

server.use(jsonServer.bodyParser);
server.use(middlewares);
server.listen(port, () => {
  console.log(`JSON Server is running at: http://localhost:${port}/`);
});

server.get(`${prefix}/brand`, (req, res) => {
  res.status(200).jsonp([
    {
      code: 1111,
      name: '銘柄1',
      market: '市場1',
      detail: '説明1',
    },
    {
      code: 2222,
      name: '銘柄2',
      market: '市場2',
      detail: '説明2',
    },
    {
      code: 3333,
      name: '銘柄3',
      market: '市場3',
      detail: '説明3',
    },
    {
      code: 4444,
      name: '銘柄4',
      market: '市場4',
      detail: '説明4',
    },
  ])
})

server.get(`${prefix}/strategy`, (req, res) => {
  res.status(200).jsonp([
    {
      sid: 1,
      uid: 1,
      gid: 1,
      label: 'すごい取引戦略',
      analysisStartDate: '2017-01-01',
      analysisEndDate: '2017-12-31',
      analyzedAt: '2018-01-01',
      memo: 'とてもすごい取引戦略です',
      cards: [
        {
          cid: 1,
          pid: null,
          label: '5日移動平均線 が 25日移動平均線 * 1.05 以上',
          used: false,
          cardType: {
            id: 1,
            label: '比較',
            key: 'compare'
          },
          leftSideIndicatorType: 1,
          leftSideDays: 5,
          rightSideIndicatorType: 1,
          rightSideDays: 25,
          rightSideFixOrFlex: false,
          rightSideFixValue: null,
          coefficient: 1.05,
          comparisonType: 2
        },
        {
          cid: 2,
          pid: null,
          label: '終値 が 25日移動平均線乖離率 * 1.05 以上',
          used: false,
          cardType: {
            id: 1,
            label: '比較',
            key: 'compare'
          },
          leftSideIndicatorType: 3,
          leftSideDays: null,
          rightSideIndicatorType: 2,
          rightSideDays: 25,
          rightSideFixOrFlex: false,
          rightSideFixValue: null,
          coefficient: 1.05,
          comparisonType: 2
        },
        {
          cid: 3,
          pid: null,
          label: '5日移動平均線 が 25日移動平均線 を 上抜け',
          used: false,
          cardType: {
            id: 2,
            label: '交差',
            key: 'cross'
          },
          leftSideIndicatorType: 3,
          leftSideDays: null,
          rightSideIndicatorType: 2,
          rightSideDays: 25,
          crossType: 1
        },
        {
          cid: 4,
          pid: null,
          label: '3日が経過',
          used: false,
          cardType: {
            id: 3,
            label: '時間',
            key: 'time'
          },
          elapsedDay: 5
        },
      ],
      inRules: [
        {
          rid: 1,
          label: '頑張る取引ルール',
          todayOrTomorrow: true,
          buyOrSell: true,
          tradeTimingType: 1,
          limitOrderPrice: null,
          orderBy: 1,
          palettes: [
            {
              pid: 1,
              leftJointType: 0,
              rightJointType: 1,
              nestOpen: true,
              nestClose: false,
              orderBy: 1
            },
            {
              pid: 2,
              leftJointType: 2,
              rightJointType: 0,
              nestOpen: false,
              nestClose: true,
              orderBy: 2
            }
          ],
        },
        {
          rid: 2,
          label: 'すごい取引ルール',
          todayOrTomorrow: false,
          buyOrSell: true,
          tradeTimingType: 3,
          limitOrderPrice: null,
          orderBy: 2,
          palettes: [
            {
              pid: 3,
              leftJointType: 0,
              rightJointType: 1,
              nestOpen: true,
              nestClose: false,
              orderBy: 1
            },
            {
              pid: 4,
              leftJointType: 2,
              rightJointType: 0,
              nestOpen: false,
              nestClose: true,
              orderBy: 2
            }
          ],
        },
      ],
      exitRules: [
        {
          rid: 3,
          label: 'えらい取引ルール',
          todayOrTomorrow: true,
          buyOrSell: true,
          tradeTimingType: 4,
          limitOrderPrice: 100,
          orderBy: 1,
          palettes: [
            {
              pid: 5,
              leftJointType: 0,
              rightJointType: 1,
              nestOpen: true,
              nestClose: false,
              orderBy: 1
            },
            {
              pid: 6,
              leftJointType: 2,
              rightJointType: 0,
              nestOpen: false,
              nestClose: true,
              orderBy: 2
            }
          ],
        },
        {
          rid: 4,
          label: 'やばい取引ルール',
          todayOrTomorrow: false,
          buyOrSell: true,
          tradeTimingType: 2,
          limitOrderPrice: null,
          orderBy: 2,
          palettes: [
            {
              pid: 7,
              leftJointType: 0,
              rightJointType: 1,
              nestOpen: true,
              nestClose: false,
              orderBy: 1
            },
            {
              pid: 8,
              leftJointType: 2,
              rightJointType: 0,
              nestOpen: false,
              nestClose: true,
              orderBy: 2
            }
          ],
        },
      ],
    },
    {
      sid: 2,
      uid: 1,
      gid: 2,
      label: 'label2',
      analysisStartDate: '2017-01-01',
      analysisEndDate: '2017-12-31',
      analyzedAt: '2018-01-01',
      memo: 'めも2',
      cards: [],
      inRules: [],
      exitRules: [],
    }
  ])
})

server.post(`${prefix}/strategy`, (req, res) => {
  if(req.body.label === 'ng') {
    res.status(400).jsonp([
      {
        message: '取引戦略作成失敗',
        type: 'warning'
      },
      {
        message: '取引戦略作成失敗',
        type: 'info'
      },
      {
        message: '取引戦略作成失敗',
        type: 'error'
      },
    ])
  }
  else {
    res.status(200).jsonp()
  }
})

server.put(`${prefix}/strategy/1`, (req, res) => {
  if(req.body.label === 'ng') {
    res.status(400).jsonp([
      {
        message: '取引戦略作成失敗',
        type: 'warning'
      },
      {
        message: '取引戦略作成失敗',
        type: 'info'
      },
      {
        message: '取引戦略作成失敗',
        type: 'error'
      },
    ])
  }
  else {
    res.status(200).jsonp()
  }
})

server.delete(`${prefix}/strategy/1`, (req, res) => {
  res.status(200).jsonp()
})

server.post(`${prefix}/card`, (req, res) => {
  res.status(200).jsonp()
})

server.put(`${prefix}/card/1`, (req, res) => {
  res.status(200).jsonp()
})

server.delete(`${prefix}/card/1`, (req, res) => {
  res.status(200).jsonp()
})

server.get(`${prefix}/analysis-brand-group`, (req, res) => {
  res.status(200).jsonp([
    {
      uid: 1,
      gid: 1,
      label: '分析銘柄グループ1',
      brands: [1111, 2222]
    },
    {
      uid: 1,
      gid: 2,
      label: '分析銘柄グループ2',
      brands: [3333, 4444]
    },
  ])
})

server.post(`${prefix}/analysis-brand-group`, (req, res) => {
  res.status(200).jsonp()
})

server.put(`${prefix}/analysis-brand-group/1`, (req, res) => {
  res.status(200).jsonp()
})

server.delete(`${prefix}/analysis-brand-group/1`, (req, res) => {
  res.status(200).jsonp()
})

// curl --noproxy localhost http://localhost:8081/sample?key=1
// req.query => Object({ key: '1' })
server.get('/sample', (req, res) => {
  console.log(req.query);
  // こんな感じでリクエストによって応答を変えられる
  if (req.query.key === '1') {
    res.status(200).jsonp({
      'message': 'ok'
    });
  } else {
    res.status(500).jsonp({
      'message': 'ng'
    });
  }
});

// curl --noproxy localhost -d 'key1=1&key2=2' http://localhost:8081/sample
// req.body => Object({ key1: '1', key2: '2' })
server.post('/sample', (req, res) => {
  console.log(req.body);
  if (req.body.key1 === '1') {
    res.status(200).jsonp({
      'message': 'ok'
    });
  } else {
    res.status(500).jsonp({
      'message': 'ng'
    });
  }
});
