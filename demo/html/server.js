const path = require("path");
const express = require("express");

const Server = {
  app: function () {
    const app = express();
    const indexPath = path.join(__dirname, "/index.html");
    const publicPath = express.static(path.join(__dirname, "/"));

    app.use("/", publicPath);
    app.get("/", function (_, res) {
      res.sendFile(indexPath);
    });

    return app;
  },
};

const port = 8088;
const app = Server.app();

app.listen(port);
console.log(`Listening at http://localhost:${port}`);