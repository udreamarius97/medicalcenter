import React, { Component } from 'react';

class EditableCell extends Component{
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <td>
                <input type='text' name={this.props.cellData.type} id={this.props.cellData.id} value={this.props.cellData.value} onChange={this.props.onProductTableUpdate}/>
            </td>
        );

    }
}

export default EditableCell;