using System.Collections.Generic;

namespace Gigshub.ViewModel
{
    public class DataCommentViewModel
    {
        public IEnumerable<CommentViewModel> Data { get; set; }
    }

    public class CommentViewModel
    {
        public string Fullname { get; set; }
        public string ImgPath { get; set; }
        public string Content { get; set; }
    }
    
    public class CommentCreateModel
    {
        public long EventId { get; set; }
        public string Content { get; set; }
    }
}