import React,{Component} from "react";

class SearchBar extends Component{
    constructor(props) {
        super(props);
        this.handleChange=this.handleChange.bind(this);
    }
    handleChange() {
        this.props.onUserInput(this.refs.filterTextInput.value);
    }
    render() {
        return (
            <div>

                <input type="text" placeholder="Search..." value={this.props.filterText} ref="filterTextInput" onChange={this.handleChange}/>

            </div>

        );
    }
}

export default SearchBar;