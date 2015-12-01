/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.notifygru.business;
/**
 * 
 * @author 
 *
 */
public class UserEmail
{
    private String _strSenderName;
    private String _strSenderEmail;
    private String _strRecipient;
    private String _strSubject;
    private String _strMessage;
    private String _strCc;
    private String _strCci;

    /**
     * @return the _strSenderName
     */
    public String getSenderName(  )
    {
        return _strSenderName;
    }

    /**
     * @param strSenderName the _strSenderName to set
     */
    public void setSenderName( String strSenderName )
    {
        this._strSenderName = strSenderName;
    }

    /**
     * @return the _strSenderEmail
     */
    public String getSenderEmail(  )
    {
        return _strSenderEmail;
    }

    /**
     * @param strSenderEmail the _strSenderEmail to set
     */
    public void setSenderEmail( String strSenderEmail )
    {
        this._strSenderEmail = strSenderEmail;
    }

    /**
     * @return the _strRecipient
     */
    public String getRecipient(  )
    {
        return _strRecipient;
    }

    /**
     * @param strRecipient the _strRecipient to set
     */
    public void setRecipient( String strRecipient )
    {
        this._strRecipient = strRecipient;
    }

    /**
     * @return the _strSubject
     */
    public String getSubject(  )
    {
        return _strSubject;
    }

    /**
     * @param strSubject the _strSubject to set
     */
    public void setSubject( String strSubject )
    {
        this._strSubject = strSubject;
    }

    /**
     * @return the _strMessage
     */
    public String getMessage(  )
    {
        return _strMessage;
    }

    /**
     * @param strMessage the _strMessage to set
     */
    public void setMessage( String strMessage )
    {
        this._strMessage = strMessage;
    }

    /**
     * @return the _strCc
     */
    public String getCc(  )
    {
        return _strCc;
    }

    /**
     * @param strCc the _strCc to set
     */
    public void setCc( String strCc )
    {
        this._strCc = strCc;
    }

    /**
     * @return the _strCci
     */
    public String getCci(  )
    {
        return _strCci;
    }

    /**
     * @param strCci the _strCci to set
     */
    public void setCci( String strCci )
    {
        this._strCci = strCci;
    }
}
