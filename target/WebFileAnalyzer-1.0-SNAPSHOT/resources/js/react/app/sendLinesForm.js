var Form = React.createClass({
  getInitialState: function(){
    return {fileName: '', lines: ''};
  },
  send: function(event){
    if(this.dontSend())
      return;
      this.errClear();
      event.preventDefault();
      this.post();
  },
  post: function(){
    var serverPath = "/WebFileAnalyzer";
    var url = "procStat";
    $.ajax({
      async: false,
      url: url,
      method: 'POST',
      data: 'fileName='+this.state.fileName+'&lines='+this.state.lines,
      headers : {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
      success: function(data) {
          window.location.replace(serverPath+"/fileStatistic.jsp?fileId="+data);
      }.bind(this),
      error: function(xhr, status, err) {
        alert('error -> status: '+status+'. Error: '+err);
      }.bind(this),
    });
  },
  dontSend: function(){
      if(this.state.fileName === undefined
            || this.state.fileName === ''){
            this.errFileName = "Field name file is required!"; 
            return true;
        }
      if(this.state.lines === undefined
            || this.state.lines === ''){
            this.errLines = "Field lines file is required!"; 
            return true;
    }
      return false;
  },
  errClear: function(){
      this.errFileName = '';
      this.errLines    = '';
  },
  handleChange: function(event){
      if(event.currentTarget.id === 'inputName'){
        this.setState({fileName: event.target.value});
        this.errFileName = '';
      }else if(event.currentTarget.id === 'textarea'){
        this.setState({lines: event.target.value});
        this.errLines    = '';
      }
  },
  render: function() {
    return (
            <div className="bd-example" >    
            <form name="form" onSubmit={this.send} method="post">
              <div className={this.errFileName?"has-error":"form-group"}>
                <label for="inputName">File name</label>
                <input onChange={this.handleChange} type="text" className="form-control" id="inputName" aria-describedby="inputNameHelp" placeholder="Enter the file name" />
                <small id="inputNameHelp" className="form-text text-muted">Give the file name</small>
                <span  className="help-block has-error"><b>{this.errFileName}</b></span>
              </div>
              <div className={this.errLines?"has-error":"form-group"} >
                <label for="textarea">Lines for counting statistics</label>
                <textarea onChange={this.handleChange} className="form-control" id="textarea" rows="14"></textarea>
                <span className="help-block has-error"><b>{this.errLines}</b></span>
              </div>
              <div className="form-actions">
                <button className={this.dontSend()?'btn btn-primary btn-block disabled':'btn btn-primary btn-block'} type="submit">Send</button>
              </div>  
             </form>
            </div>
    );
  }
});

ReactDOM.render(
  <Form />,
  document.getElementById('form')
);