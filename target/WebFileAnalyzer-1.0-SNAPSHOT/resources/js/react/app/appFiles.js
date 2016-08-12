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

var ListFiles = React.createClass({
  mixins: [GetData], 
  getInitialState: function() {
    return {filterCheck: false};
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
  handleClick: function(event) { 
    var check;
    if(event.currentTarget instanceof HTMLInputElement){
        this.setState({filterCheck: !this.state.filterCheck});
        check = !this.state.filterCheck;
    }else {
        check = this.state.filterCheck;
        if(event.currentTarget.id === 'next')
            this.rowsFrom += 100;
        else if(event.currentTarget.id === 'prev'
                && this.rowsFrom > 0)
            this.rowsFrom -= 100;
    }
    var args = this.getArgs();
    this.get(check?"getFiles"+args+"&moreThen="+this.moreThan:"getFiles"+args);
  },  
  isFilterCheck: function() {
    return {filterCheck: this.state.filterCheck};
  },
  
  isNotBlockedState: function() {
    return this.state.files[0] !== undefined;
  },
  render: function() {
    var keys = this.state.keys;
    var keysList;
    if(keys !== undefined){
        keysList = keys.map(function(key){
                    return <td>{key}</td>;
                  })
        var files = this.state.files;
        var fileList;
        if(files !== undefined)
            fileList = files.map(function(file){
                var hrefAtr = "fileStatistic.jsp?fileId="+file['id'];
                var procDate = moment(file['procDate']).format("YYYY-MM-DD HH:mm:ss");
                return <tr> 
                            <td>{file['id']}</td>
                            <td><a href={hrefAtr} target="_blank">{file['fileName']}</a></td>
                            <td>{procDate}</td>
                       </tr>;
            })
    }
    return (
            <div>
                <SendButton />
                <label className="form-check-label">
                    <input id="form-check-input" className="form-check-input" type="checkbox" value=""  onClick={this.handleClick} />&nbsp;files with more than 10 lines
                </label>
            <table className="table table-bordered table-striped" >
                <thead>
                    <tr>{keysList}
                    </tr>    
                </thead>
                <tbody>
                {fileList}
                </tbody>
            </table> 
                <ul className="pager">
                    <li className={this.rowsFrom > 0?'':'disabled'} id="prev" onClick={this.rowsFrom > 0?this.handleClick:undefined} ><a href="#">Prev&nbsp;</a></li>
                    <li className={this.isNotBlockedState()?'':'disabled'} id="next" onClick={this.isNotBlockedState()?this.handleClick:undefined} ><a href="#">Next</a></li>
                </ul>
            </div>
    );
  },
  
  componentDidMount: function() {
     this.filterCheck = false;
     this.get("getFiles"+this.getArgs());
  }
  
});

ReactDOM.render(
  <ListFiles />,
  document.getElementById('table')
);

