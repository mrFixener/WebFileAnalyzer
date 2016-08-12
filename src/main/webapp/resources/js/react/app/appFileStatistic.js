var GetData = {
    getInitialState: function() {
        return {keys: [], 
                files: [], 
                moreThan: 10, 
                rowsFrom: this.props.rowsFrom,
                step: this.props.step
            };
    },
    componentDidMount: function() {
        this.files = [];
        this.keys = [];
        this.moreThan = 10;
        this.rowsFrom = 0;
        this.step   = 100;
    },
    setData: function(k, f) {
        this.setState({
                keys : k,
                files: f,
        });
    },
    getArgs: function() {
        return '?from='+this.rowsFrom+'&qty='+this.step;
    }
};

var SendButton = React.createClass({
  render: function() {
    return (
            <div className="alert alert-info container">
                <div className="pull-right">
                    <a className="btn btn-secondary" href="sendingLines.jsp" target="_blank" role="button">Send to statistics</a>
                </div>   
            </div>
    );
  }
});

var TheadTr = React.createClass({
  render: function() {
    return (
            <tr>
                <td>Record id</td>
                <td>File id in DB</td>
                <td>Line</td>
                <td>Longest word<br/>(symbols between 2 spaces)</td>
                <td>Shortest word</td>
                <td>Line length</td>
                <td>Average word length</td>
            </tr>
    );
  }
});

var ListStatisFiles = React.createClass({
  mixins: [GetData],
  getInitialState: function() {
    return {fileId: this.props.fileId};
  },
  get: function(url) {
     this.serverRequest = $.get(url, function (result) {
      var files = result.files;
      var k = [];
      if(files !== undefined
        && files[0] !== undefined){
            k = Object.keys(files[0]); 
       }
       this.setData(k, files);
    }.bind(this));
  },
  getParameterByName: function (name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
  },
  handleClick: function(event) { 
    if(event.currentTarget.id === 'next')
        this.rowsFrom += this.step;
    else if(event.currentTarget.id === 'prev'
            && this.rowsFrom > 0)
        this.rowsFrom -= this.step;
    var args = this.getArgs();
    this.serverRequest = $.get("getFileStat/"+this.fileId+args, function (result) {
      var files = result.fileStat;
      var k = [];
      if(files !== undefined
        && files[0] !== undefined){
            k = Object.keys(files[0]);
       }
       this.setData(k, files);
    }.bind(this));
  },
   isNotBlockedState: function() {
    return this.state.files[0] !== undefined ;
  },
  render: function() {
    var keys = this.state.keys;
    if(keys !== undefined){
        var files = this.state.files;
        var fileList;
        if(files !== undefined)
            fileList = files.map(function(file){
                return <tr> 
                            <td>{file['internalId']}</td>
                            <td>{file['externalId']}</td>
                            <td>{file['line']}</td>
                            <td>{file['maxWord']}</td>
                            <td>{file['minWord']}</td>
                            <td>{file['lengthLine']}</td>
                            <td>{file['avgWord']}</td>
                       </tr>;
            })
    }
    return (
            <div>
                <SendButton />
                <ul className="pager">
                    <li className={this.rowsFrom > 0?'':'disabled'} id="prev" onClick={this.rowsFrom > 0?this.handleClick:undefined} ><a href="#">Prev&nbsp;</a></li>
                    <li className={this.isNotBlockedState()?'':'disabled'} id="next" onClick={this.isNotBlockedState()?this.handleClick:undefined} ><a href="#">Next</a></li>
                </ul>
            <table className="table table-bordered table-striped" >
                <thead>
                    <TheadTr />
                </thead>
                <tbody>
                    {fileList}
                </tbody>
            </table> 
                <ul className="pager">
                    <li className={this.rowsFrom > 0?'':'disabled'} id="prev" onClick={this.rowsFrom > 0?this.handleClick:undefined} ><a href="#">Prev&nbsp;</a></li>
                    <li className={this.isNotBlockedState()?'':'disabled'} id="next" onClick={this.isNotBlockedState()?this.handleClick:undefined} ><a href="#">Next</a> </li>
                </ul>
            </div>
    );
  },
  
  componentDidMount: function() {
     this.fileId = this.getParameterByName('fileId');
      this.serverRequest = $.get("getFileStat/"+this.fileId+this.getArgs(), function (result) {                          
      var files = result.fileStat;
      var k = [];
      if(files !== undefined
        && files[0] !== undefined){
            k = Object.keys(files[0]);
       }
       this.setData(k, files);
    }.bind(this));
  }
  
});

ReactDOM.render(
  <ListStatisFiles />,
  document.getElementById('components')
);


