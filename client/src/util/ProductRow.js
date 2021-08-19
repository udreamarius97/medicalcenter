import React, { Component } from 'react';
import EditableCell from "./EditableCell";

class ProductRow extends Component{
    constructor(props) {
        super(props);
    }
    onDelEvent() {
        this.props.onDelEvent(this.props.product);

    }
    onUpdateEvent() {
        this.props.onUpdateEvent(this.props.product);
    }
    render() {
        return (
            <tr className="eachRow">
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "name",
                    value: this.props.product.name,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "address",
                    value: this.props.product.address,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "birthdate",
                    value: this.props.product.birthdate,
                    id: this.props.product.id
                }}/>
               <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "gender",
                    value: this.props.product.gender,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "email",
                    value: this.props.product.email,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "username",
                    value:this.props.product.username,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "role",
                    value:this.props.product.role,
                    id: this.props.product.id
                }}/>
                <td className="del-cell">
                    <input type="button" onClick={this.onDelEvent.bind(this)} value="X" className="del-btn"/>
                </td>
                <td className="add-cell">
                    <input type="button" onClick={this.onUpdateEvent.bind(this)} value="+" className="add-btn"/>
                </td>
            </tr>
        );
    }
}

export default ProductRow;